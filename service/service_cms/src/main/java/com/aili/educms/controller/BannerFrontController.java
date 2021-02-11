package com.aili.educms.controller;

import com.aili.commonutils.R;
import com.aili.educms.entity.CrmBanner;
import com.aili.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 艾力
 * @date 2021/1/25 15:01
 * 前端Banner显示用
 **/

@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.getAllBanner();
        return R.ok().data("list",list);
    }

}
