package com.aili.eduservice.entity.vo;

/**
 * @author 艾力
 * @date 2021/1/21 21:11
 **/

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 确认发布课程  信息预览
 */
@Data
@ApiModel(value = "课程信息预览")
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
