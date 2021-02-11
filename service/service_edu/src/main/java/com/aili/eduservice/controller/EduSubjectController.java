package com.aili.eduservice.controller;


import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduSubject;
import com.aili.eduservice.entity.subject.SubjectList;
import com.aili.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-17
 */

@Api("课程管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传的文件 并读取内容
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSunject(MultipartFile file){
        //上传过来的文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //查询所有的课程列表
    @ApiOperation(value = "查询所有的课程列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){

        List<SubjectList> subjectLists = subjectService.getAllSubject();
        return R.ok().data("subjects",subjectLists);
    }


}

