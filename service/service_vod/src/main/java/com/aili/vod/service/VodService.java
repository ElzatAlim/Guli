package com.aili.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/22 20:27
 **/


public interface VodService {


    //上传视频
    String uploadVideoAly(MultipartFile file);

    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List<String> videoIdList);
}
