package com.aili.educms.controller;


import com.aili.commonutils.R;
import com.aili.educms.entity.CrmBanner;
import com.aili.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器    管理员使用
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-25
 */


@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    //分页查询Banner
        @Autowired
        private CrmBannerService bannerService;

        @GetMapping("pageBanner/{current}/{limit}")
        @ApiOperation(value = "讲师分页查询")
        public R pageListBanner(
        @ApiParam(name = "current",value = "当前页",required = true) @PathVariable long current,
        @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit){
        //创建page对象
        Page<CrmBanner> crmBannerPage = new Page<>(current,limit);
        //调用方法实现分页
        bannerService.page(crmBannerPage, null);

        long total = crmBannerPage.getTotal();
        List<CrmBanner> records = crmBannerPage.getRecords();

        return R.ok().data("total",total).data("list",records);
    }

        //添加Banner的方法
        @CacheEvict(value = "banner",allEntries = true)
        @PostMapping("addBanner")
        @ApiOperation(value = "添加Banner")
        public R addBanner(@RequestBody(required = true) CrmBanner crmBanner){
        boolean save = bannerService.save(crmBanner);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

        //删除指点ID的教师
        @CacheEvict(value = "banner",allEntries = true)
        @DeleteMapping("remove/{id}")
        @ApiOperation(value = "Banner根据ID删除")
        public R removeBanner(@ApiParam(name = "id",value = "BannerID",required = true) @PathVariable String id){
        boolean flag = bannerService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

        //根据讲师ID查询
        @GetMapping("geTBanner/{id}")
        @ApiOperation(value = "根据BannerID查询")
        public R getBanner(@ApiParam(name = "id",value = "BannerID",required = true)@PathVariable String id){
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("banner",crmBanner);
    }

        //根据BannerID修改方法
        @CacheEvict(value = "banner",allEntries = true)
        @PostMapping ("update")
        @ApiOperation(value = "修改Banner")
        public R updateBanner(@RequestBody CrmBanner crmBanner){
        boolean flag = bannerService.updateById(crmBanner);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

