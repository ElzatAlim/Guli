package com.aili.eduservice.service;

import com.aili.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-19
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程ID删除
    void removeByCourseId(String id);
}
