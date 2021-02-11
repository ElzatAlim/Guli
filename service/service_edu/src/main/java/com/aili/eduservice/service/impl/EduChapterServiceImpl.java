package com.aili.eduservice.service.impl;

import com.aili.eduservice.client.VodClient;
import com.aili.eduservice.entity.EduChapter;
import com.aili.eduservice.entity.EduVideo;
import com.aili.eduservice.entity.chapter.ChapterVo;
import com.aili.eduservice.entity.chapter.VideoVo;
import com.aili.eduservice.mapper.EduChapterMapper;
import com.aili.eduservice.service.EduChapterService;
import com.aili.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;


    //课程大纲列表  根据课程ID查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //最终要的到的数据列表
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<>();
        //获取章节信息
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
        queryWrapper1.orderByAsc("sort", "id");
        List<EduChapter> chapters = baseMapper.selectList(queryWrapper1);
        //获取课时信息
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        queryWrapper2.orderByAsc("sort", "id");
        List<EduVideo> videos = videoService.list(queryWrapper2);
        //填充章节vo数据
        int count1 = chapters.size();
        for (int i = 0; i < count1; i++) {
            EduChapter chapter = chapters.get(i);
            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVo.setLabel(chapter.getTitle());
            chapterVoArrayList.add(chapterVo);
            //填充课时vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            int count2 = videos.size();
            for (int j = 0; j < count2; j++) {
                EduVideo video = videos.get(j);
                if(chapter.getId().equals(video.getChapterId())){
                    //创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVo.setLabel(video.getTitle());
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }
        return chapterVoArrayList;
    }

    //新增  删除
    @Override
    public void saveOrUpdateChapter(List<ChapterVo> list, String courseId) {
        System.out.println("====="+courseId+"======");
        //删除
        Map<String,Object> map = new HashMap<>();
        map.put("course_id",courseId);
        //先找出所有的视频ID
        Collection<EduVideo> eduVideos = videoService.listByMap(map);
        List<String> videosId = new ArrayList<>();
        for (EduVideo video : eduVideos) {
            videosId.add(video.getVideoSourceId());
        }
        //从阿里云删除视频   并且 删除数据库数据
        if(videosId.size()>0){
            vodClient.deleteBatch(videosId);
        }
        videoService.removeByMap(map);
        // 再删除一级
        baseMapper.deleteByMap(map);
        //获取一级章节 和  二级章节  并且排序（sort）
//        ArrayList<EduChapter> eduChapters = new ArrayList<>();
//        ArrayList<EduVideo> eduVideos = new ArrayList<>();
        int sort = 0;
        for (ChapterVo chapterVo : list) {
            EduChapter eduChapter = new EduChapter();
            eduChapter.setCourseId(courseId);
            eduChapter.setTitle(chapterVo.getLabel());
            eduChapter.setSort(++sort);
            baseMapper.insert(eduChapter);
            if(null!=chapterVo.getChildren()&&chapterVo.getChildren().size()>0){
                for (VideoVo child : chapterVo.getChildren()) {
                    EduVideo eduVideo  = new EduVideo();
                    eduVideo.setChapterId(eduChapter.getId());
                    eduVideo.setCourseId(courseId);
                    eduVideo.setTitle(child.getLabel());
                    eduVideo.setSort(++sort);
                    videoService.save(eduVideo);
                }
            }
        }
    }

    //根据课程ID删除
    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
