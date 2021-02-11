package com.aili.eduservice.controller;


import com.aili.baseservice.exceptionHandler.GuliException;
import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduTeacher;
import com.aili.eduservice.entity.vo.TeacherQuery;
import com.aili.eduservice.service.EduTeacherService;
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

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-12
 * //自定义抛出异常
 *   try{
 *             int i = 10/0;
 *         }catch (Exception e){
 *             throw new GuliException(200001,"执行了自定义异常处理！");
 *         }
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {


    @Autowired
    private EduTeacherService teacherService;

    //查询教师表中的所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("item",list);
    }

    //删除指点ID的教师
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @DeleteMapping("{id}")
    @ApiOperation(value = "教师根据ID删除")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "讲师分页查询")
    public R pageListTeacher(
            @ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        //调用方法实现分页
        teacherService.page(teacherPage, null);

        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    //条件查询带方法
//    @GetMapping("pageTeacherCondition/{current}/{limit}")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation(value = "讲师条件分页组合查询")
    public R pageTeacherCondition(
            @ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit,
//            如果这行加上RequestBody（required = false）注解的话参数时Json格式的参数
//            如果加上的话  时接受不了get请求的  所以 方法必须改成@PostMapping
            @RequestBody(required = false) TeacherQuery teacherQuery
    ){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建一个QueryWrapper  判断条件之是否为空 如果不为空就拼接条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //构建条件
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name", name);         //模糊
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);         //等于
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);    //大于
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);      //小于
        }
        wrapper.orderByDesc("gmt_modified"); //修改时间排序
        //调用方法实现分页
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }


    //添加讲师的方法
    @PostMapping("addTeacher")
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @ApiOperation(value = "添加讲师的方法")
    public R addTeacher(@RequestBody(required = true) EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据讲师ID查询
    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据讲师ID查询")
    public R getTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //根据讲师修改方法
    @CacheEvict(value = "teacherAndCourse",allEntries = true)
    @PostMapping ("updateTeacher")
    @ApiOperation(value = "修改讲师")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

