package com.aili.eduservice.controller.front;

import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.EduTeacher;
import com.aili.eduservice.service.EduCourseService;
import com.aili.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/25 15:54
 **/

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条老师
    @Cacheable(key = "'selectIndexList'",value = "teacherAndCourse")
    @GetMapping("index")
    public R index(){
        //查询前8热门课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("gmt_create");
        courseWrapper.last("limit 8");
        List<EduCourse> eduCourses = courseService.list(courseWrapper);

        //查询前4热门名师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("sort");
        teacherWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = teacherService.list(teacherWrapper);

        return R.ok().data("eduList",eduCourses).data("teacherList",eduTeachers);
    }

}
