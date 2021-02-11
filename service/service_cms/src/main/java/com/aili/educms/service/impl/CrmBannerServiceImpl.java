package com.aili.educms.service.impl;

import com.aili.educms.entity.CrmBanner;
import com.aili.educms.mapper.CrmBannerMapper;
import com.aili.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> getAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
//        wrapper.last("limit 2");
        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);
        return bannerList;
    }
}
