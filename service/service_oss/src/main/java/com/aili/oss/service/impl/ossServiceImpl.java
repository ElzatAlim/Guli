package com.aili.oss.service.impl;

import com.aili.oss.service.OssService;
import com.aili.oss.utils.ConstantPropertiesUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 艾力
 * @date 2021/1/15 19:59
 **/

@Service
public class ossServiceImpl implements OssService {

    //上传头像
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;


        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名称   拼接随意ID
            String filename = file.getOriginalFilename();
            String uuid  = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid+filename;

            //  存放目录
            String datepath = new DateTime().toString("yyyy/MM/dd");
            // 2020/01/15/dawdajdkdadaa.jpg
            filename = datepath+"/"+filename;

            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();
            //拼接路径并返回
            //https://edu-aili.oss-cn-shanghai.aliyuncs.com/touxiang.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
