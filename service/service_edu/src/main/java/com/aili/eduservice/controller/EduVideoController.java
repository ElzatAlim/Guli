package com.aili.eduservice.controller;


import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduVideo;
import com.aili.eduservice.entity.chapter.ChapterVo;
import com.aili.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/eduservice/video")
@Api("章节管理")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //课程大纲列表  根据课程ID查询
    @ApiOperation("根据课程ID查询小节")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        Map<String,Object> map = new HashMap<>();
        map.put("course_id",courseId);
        Collection<EduVideo> eduVideos = videoService.listByMap(map);
        return R.ok().data("eduVideos",eduVideos);
    }


    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //TODO 后面这个方法需要完善
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }


}

