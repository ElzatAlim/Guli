package com.aili.statistics.controller;


import com.aili.commonutils.R;
import com.aili.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计某一天注册人数  生成统计数据
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){

        statisticsDailyService.registerCount(day);

        return R.ok();
    }

    //图表显示
    @GetMapping("showDate/{begin}/{end}")
    public R showDate(@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map  =statisticsDailyService.showDate(begin,end);
        return R.ok().data(map);
    }

}

