package com.aili.eduservice.service.impl;

import com.aili.baseservice.exceptionHandler.GuliException;
import com.aili.eduservice.client.VodClient;
import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.EduCourseDescription;
import com.aili.eduservice.entity.EduVideo;
import com.aili.eduservice.entity.frontvo.CourseQueryVo;
import com.aili.eduservice.entity.frontvo.CourseWebVo;
import com.aili.eduservice.entity.vo.CourseInfoVo;
import com.aili.eduservice.entity.vo.CoursePublishVo;
import com.aili.eduservice.mapper.EduCourseMapper;
import com.aili.eduservice.service.EduChapterService;
import com.aili.eduservice.service.EduCourseDescriptionService;
import com.aili.eduservice.service.EduCourseService;
import com.aili.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private VodClient vodClient;

    //添加课程基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程基本信息
        //CourseInfoVo对象转换成eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = this.baseMapper.insert(eduCourse);

        if (insert <= 0) {
            //抛出异常
            throw new GuliException(20001, "添加课程失败！");
        }

        //获取添加之后的客户才能ID
        String cid = eduCourse.getId();

        //向课程简介表添加客户才能简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述ID和课程ID一样
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //查询课程信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        System.out.println(courseId);
        System.out.println(eduCourse);
        System.out.println("============");
        System.out.println(courseInfoVo);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuliException(20001, "课程信息修改失败");
        }

        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(eduCourseDescription);
    }

    //根据课程ID查询课程信息
    @Override
    public CoursePublishVo PublishCourseInfo(String courseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("course_id", courseId);
        int size = videoService.listByMap(map).size();
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setLessonNum(size);
        baseMapper.updateById(eduCourse);

        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);

        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String id) {

        //删除小节 (先)
        //删除
        Map<String, Object> map = new HashMap<>();
        map.put("course_id", id);
        //先找出所有的视频ID
        Collection<EduVideo> eduVideos = videoService.listByMap(map);
        List<String> videosId = new ArrayList<>();
        for (EduVideo video : eduVideos) {
            videosId.add(video.getVideoSourceId());
        }
        //从阿里云删除视频   并且 删除数据库数据
        if (videosId.size() > 0) {
            vodClient.deleteBatch(videosId);
        }
        videoService.removeByCourseId(id);


        //删除章节
        chapterService.removeByCourseId(id);

//        删除课程描述
        courseDescriptionService.removeById(id);

//        删除课程
        int i = baseMapper.deleteById(id);

        if (i == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    //前端课程列表分页查询
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageParam, CourseQueryVo courseQuery) {


        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //一级ID
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }
        //二级ID
        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            wrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        //排序方式
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            if(courseQuery.getPriceSort().equals("1"))
                wrapper.orderByDesc("price");
            if(courseQuery.getPriceSort().equals("0"))
                wrapper.orderByAsc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
