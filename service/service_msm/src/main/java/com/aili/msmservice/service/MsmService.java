package com.aili.msmservice.service;

import java.util.Map;

/**
 * @author 艾力
 * @date 2021/1/26 11:22
 **/

public interface MsmService {
    //返送短信
    boolean send(Map<String, Object> param, String phone);
}
