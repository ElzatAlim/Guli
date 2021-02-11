package com.aili.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/22 16:52
 **/
public class TestVod {

    public static void main(String[] args) throws ClientException {

        String accessKeyId= "LTAI4GD4834L8wNUFDJpv7dK";
        String accessKeySecret = "euM2wo4deDWkzyk2DrMDiKLlUjhPuq";
        String title = "SDK-Upload";
        String fileName = "D:\\艾力学院\\在线教育--谷粒学院\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }


    }

    //获取凭证
    public void getPlayAuth() throws ClientException {
        //获取视频播放凭证
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GD4834L8wNUFDJpv7dK", "euM2wo4deDWkzyk2DrMDiKLlUjhPuq");

        //创建获取视频播放凭证的request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("fb4b9951ea2648b08d08fc9e3409b79d");

        response = client.getAcsResponse(request);

        System.out.println("视频播放凭证："+response.getPlayAuth());
    }

    //获取视频播放地址
    public void getPlayUrl() throws ClientException {
        //根据视频ID获取播放地址
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GD4834L8wNUFDJpv7dK", "euM2wo4deDWkzyk2DrMDiKLlUjhPuq");

        //创建获取视频地址的request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request赋值视频ID
        request.setVideoId("fb4b9951ea2648b08d08fc9e3409b79d");

        //初始化  传递request  获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
