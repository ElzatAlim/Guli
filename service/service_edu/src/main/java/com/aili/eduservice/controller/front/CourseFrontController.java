package com.aili.eduservice.controller.front;

import com.aili.commonutils.R;
import com.aili.commonutils.ordervo.CourseWebVoOrder;
import com.aili.eduservice.entity.EduCourse;
import com.aili.eduservice.entity.chapter.ChapterVo;
import com.aili.eduservice.entity.frontvo.CourseQueryVo;
import com.aili.eduservice.entity.frontvo.CourseWebVo;
import com.aili.eduservice.service.EduChapterService;
import com.aili.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 艾力
 * @date 2021/1/30 21:59
 **/

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.getFrontCourseList(pageParam, courseQuery);
        return R.ok().data(map);
    }

    //课程详细
    @ApiOperation(value = "课程信息查看")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){

        //查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //章节小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    //根据课程id返回课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){

        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder course = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo, course);
        return course;
    }

}
