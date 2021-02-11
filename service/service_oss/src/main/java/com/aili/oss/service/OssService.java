package com.aili.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 艾力
 * @date 2021/1/15 19:59
 **/
public interface OssService {
    //上传头像
    String uploadFileAvatar(MultipartFile file);
}
