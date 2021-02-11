package com.aili.eduservice.service;

import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.frontvo.CourseQueryVo;
import com.aili.eduservice.entity.frontvo.CourseWebVo;
import com.aili.eduservice.entity.vo.CourseInfoVo;
import com.aili.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //查询课程信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程ID查询课程信息
    CoursePublishVo PublishCourseInfo(String courseId);

    //删除课程
    void removeCourse(String id);

    //前端课程列表分页查询
    Map<String, Object> getFrontCourseList(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    CourseWebVo getBaseCourseInfo(String courseId);
}
