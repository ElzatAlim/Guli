package com.aili.statistics.service.impl;

import com.aili.commonutils.R;
import com.aili.statistics.client.UcenterClient;
import com.aili.statistics.entity.StatisticsDaily;
import com.aili.statistics.mapper.StatisticsDailyMapper;
import com.aili.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-06
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计某一天注册人数  生成统计数据
    @Override
    public void registerCount(String day) {
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        //删除原有的当前日的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);
        Random random = new Random();
        sta.setVideoViewNum(random.nextInt(20));
        sta.setLoginNum(random.nextInt(10));
        sta.setCourseNum(random.nextInt(10));

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> showDate(String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);

        Map<String, Object> map = new HashMap<>();
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);
        List<Integer> dataList_login_num = new ArrayList<Integer>();
        List<Integer> dataList_video_view_num = new ArrayList<Integer>();
        List<Integer> dataList_course_num = new ArrayList<Integer>();
        List<Integer> dataList_register_num = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {
            StatisticsDaily daily = list.get(i);
            dateList.add(daily.getDateCalculated());
            dataList_login_num.add(daily.getLoginNum());
            dataList_video_view_num.add(daily.getVideoViewNum());
            dataList_course_num.add(daily.getCourseNum());
            dataList_register_num.add(daily.getRegisterNum());
        }

        map.put("dataList_login_num", dataList_login_num);
        map.put("dataList_video_view_num", dataList_video_view_num);
        map.put("dataList_course_num", dataList_course_num);
        map.put("dataList_register_num", dataList_register_num);
        map.put("dateList", dateList);

        return map;
    }
}
