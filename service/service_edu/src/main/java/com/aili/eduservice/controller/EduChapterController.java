package com.aili.eduservice.controller;


import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduChapter;
import com.aili.eduservice.entity.chapter.ChapterVo;
import com.aili.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api("课程大纲管理")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表  根据课程ID查询
    @ApiOperation("课程大纲列表  根据课程ID查询")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加或修改章节
    @PostMapping("saveOrUpdateChapter/{courseId}")
    @ApiOperation("添加章节")
    public R saveOrUpdateChapter(@RequestBody List<ChapterVo> list,@PathVariable String courseId){
        chapterService.saveOrUpdateChapter(list,courseId);
        return R.ok();
    }




}

