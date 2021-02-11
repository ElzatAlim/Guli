package com.aili.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/20 14:29
 **/

@Data
public class ChapterVo {

    private String id;

    private String label;

    private List<VideoVo> children = new ArrayList<>();

}
