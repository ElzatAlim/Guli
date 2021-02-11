package com.aili.educenter.controller;

import com.aili.baseservice.exceptionHandler.GuliException;
import com.aili.commonutils.JwtUtils;
import com.aili.educenter.entity.UcenterMember;
import com.aili.educenter.service.UcenterMemberService;
import com.aili.educenter.utils.ConstantPropertiesUtil;
import com.aili.educenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author 艾力
 * @date 2021/1/28 20:52
 **/
@Controller
@RequestMapping("api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;
    //    获取扫码人信息

    @GetMapping("callback")
    public String callback(String code,String state){
        try {
//        用code请求微信固定的地址  得到两个值accesstoken和openid
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //拼接三个三叔
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        //使用HttpClient 请求拼接好的地址  返回两个值
            String s = HttpClientUtils.get(accessTokenUrl);
            System.out.println("token:"+s);

            //解析json字符串
            Gson gson = new Gson();
            HashMap map = gson.fromJson(s, HashMap.class);
            String accessToken = (String)map.get("access_token");
            String openid = (String)map.get("openid");

            //判断是否存在
            //添加到数据库   openid  昵称  头像
            UcenterMember member =  memberService.getOpenIdMember(openid);
            if(member==null){//添加
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String resultUserInfo = null;

                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);

//                解析json
                HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo,
                        HashMap.class);
                String nickname = (String)mapUserInfo.get("nickname");  //昵称
                String headimgurl = (String)mapUserInfo.get("headimgurl");      //头像

                member =  new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

//            使用JWT生成token  通过路径传过去
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());



            return "redirect:http://localhost:3000?token="+token;
            //TODO 登录

        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }
    }



    @GetMapping("login")
    public String genQrConnect(HttpSession session) {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "ElzatAlim";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;

    }



}
