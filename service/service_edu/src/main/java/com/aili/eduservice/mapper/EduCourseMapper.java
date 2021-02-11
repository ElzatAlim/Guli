package com.aili.eduservice.mapper;

import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.frontvo.CourseWebVo;
import com.aili.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
