package com.aili.eduservice.controller.front;

import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.EduTeacher;
import com.aili.eduservice.service.EduCourseService;
import com.aili.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 艾力
 * @date 2021/1/29 13:58
 **/
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师的方法

    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){

        Page<EduTeacher>  teacherPage= new Page<>(page,limit);
        Map<String,Object> map =  teacherService.getTeacherFrontList(teacherPage);

        //返回分页中的所有数据
        return R.ok().data(map);
    }


    //讲师信息查询
    @GetMapping("getTeacherFrontInfo/{id}")
    public R getTeacherFrontInfo(@PathVariable String id){

        EduTeacher teacher = teacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }


}
