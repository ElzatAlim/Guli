package com.aili.eduservice.controller;


import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.EduTeacher;
import com.aili.eduservice.entity.vo.CourseInfoVo;
import com.aili.eduservice.entity.vo.CoursePublishVo;
import com.aili.eduservice.entity.vo.CourseQuery;
import com.aili.eduservice.service.EduChapterService;
import com.aili.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */

@Api("课程信息管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //查询所有课程
    @ApiOperation(value = "查询所有课程信息")
    @PostMapping("findAll")
    public R findAll(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //删除指点ID的课程
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @DeleteMapping("{id}")
    @ApiOperation(value = "课程根据ID删除")
    public R removeCourse(@ApiParam(name = "id",value = "课程ID",required = true) @PathVariable String id){
         courseService.removeCourse(id);
         return R.ok();
    }


    //查询所有课程带分页
    @ApiOperation(value = "查询所有课程信息分页")
    @PostMapping("getCourseList/{current}/{limit}")
    public R getCourseList( @ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
                            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit,
                            @RequestBody(required = false) CourseQuery courseQuery
    ){


        Page<EduCourse> page = new Page<>(current,limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String id = courseQuery.getId();
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String status = courseQuery.getStatus();
        BigDecimal min_price = courseQuery.getMin_price();
        BigDecimal max_price = courseQuery.getMax_price();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        //构建条件
        if (!StringUtils.isEmpty(id)){
            wrapper.like("id", id);         //模糊
        }
        if (!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);         //模糊
        }
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title", title);         //模糊
        }
        if (!StringUtils.isEmpty(status)&&!status.equals("0")){
            wrapper.eq("status", status);         //等于
        }
        if (!StringUtils.isEmpty(min_price)&& min_price.intValue()!=0){
            wrapper.ge("price", min_price);    //大于
        }
        if (!StringUtils.isEmpty(max_price)&& min_price.intValue()!=0){
            wrapper.le("price", max_price);      //小于
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);    //大于
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);      //小于
        }
        wrapper.orderByDesc("gmt_modified"); //修改时间排序


        courseService.page(page, wrapper);

        long total = page.getTotal();
        List<EduCourse> list = page.getRecords();
        return R.ok().data("total",total).data("list",list);
    }

    //添加课程基本信息
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //查询课程信息
    @GetMapping("getCourseInfo/{courseId}")
    @ApiOperation(value = "查询课程基本信息")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo =  courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @PostMapping("updateCourseInfo")
    @ApiOperation(value = "修改课程基本信息")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程ID查询课程信息
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.PublishCourseInfo(courseId);

        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @PostMapping("publishCourse/{id}")
    @ApiOperation(value="客户才能的最后发布")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse =new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }
}

