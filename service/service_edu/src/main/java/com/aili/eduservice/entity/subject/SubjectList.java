package com.aili.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/19 10:46
 **/

@Data
public class SubjectList {
    private String id;
    private String label;

    private List<SubjectList> children;

}
