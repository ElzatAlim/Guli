package com.aili.statistics.client;

import com.aili.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 艾力
 * @date 2021/2/6 21:18
 **/

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);

}
