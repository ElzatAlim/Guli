package com.aili.eduorder.client;

import com.aili.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 艾力
 * @date 2021/2/5 15:19
 **/
@Component
@FeignClient("service-edu") //第二个参数 可以找实现类.class  找不到的时候调用
public interface EduClient {

    //根据课程id返回课程信息  注意  远程调用PathVariable 必须加参数名
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id);
}
