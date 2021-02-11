package com.aili.eduorder.client;

import com.aili.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 艾力
 * @date 2021/2/5 15:19
 **/
@Component
@FeignClient("service-ucenter") //第二个参数 可以找实现类.class  找不到的时候调用
public interface UcenterClient {

    //根据用户Id获取用户信息    注意  远程调用PathVariable 必须加参数名
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);

}
