package com.aili.eduservice.client;

import com.aili.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 艾力
 * @date 2021/1/23 16:21
 **/

//熔断机制   超时或者找不到服务

@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了！");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了！");
    }
}
