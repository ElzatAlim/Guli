package com.aili.eduservice.service;

import com.aili.eduservice.entity.EduChapter;
import com.aili.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
public interface EduChapterService extends IService<EduChapter> {

    //课程大纲列表  根据课程ID查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //新增  修改
    void saveOrUpdateChapter(List<ChapterVo> list,String courseId);

    //根据课程id删除
    void removeByCourseId(String id);
}
