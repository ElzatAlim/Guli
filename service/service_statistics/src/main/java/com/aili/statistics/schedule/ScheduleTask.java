package com.aili.statistics.schedule;

import com.aili.statistics.service.StatisticsDailyService;
import com.aili.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 艾力
 * @date 2021/2/7 12:45
 **/

@Component
public class ScheduleTask {

    @Autowired
    StatisticsDailyService statisticsDailyService;

    //每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task1(){
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }

}
