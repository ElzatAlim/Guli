package com.aili.eduservice.controller.front;


import com.aili.commonutils.R;
import com.aili.eduservice.entity.EduComment;
import com.aili.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    //分页获取所有评论   根据course_id
    @GetMapping("getAllComment/{current}/{limit}/{courseId}")
    public R getAllComment(@PathVariable long current,@PathVariable long limit,@PathVariable String courseId){

        Page<EduComment> page = new Page<EduComment>(current,limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByDesc("gmt_create");
        commentService.page(page,wrapper);

        long total = page.getTotal();
        List<EduComment> comments = page.getRecords();

        return R.ok().data("total",total).data("list",comments);
    }


    //发布评论
    @PostMapping("saveComment")
    public R saveComment(@RequestBody EduComment eduComment){
        commentService.save(eduComment);
        return R.ok();
    }



    //删除评论



}

