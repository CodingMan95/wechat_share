package com.eim.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eim.config.WXConfig;
import com.eim.entity.User;
import com.eim.service.UserService;
import com.eim.service.WechatService;
import com.eim.util.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class WechatServiceImpl implements WechatService {
    @Autowired
    private WXConfig wxConfig;

    @Override
    public JSONObject getOpenid(String code) {
        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //////////////// ////////////////
        // 请求参数
        String params = "appid=" + wxConfig.wxspAppid + "&secret=" + wxConfig.wxspSecret + "&js_code=" + code + "&grant_type="
                + wxConfig.grantType;
        // 发送请求
        String sr = HttpRequest.sendGet(wxConfig.code2Session, params);
        // 解析相应内容（转换成json对象）
        JSONObject json = JSONObject.parseObject(sr);
        JSONObject jsonObject = new JSONObject();

        String openid = json.get("openid").toString();
        log.info(openid);
        jsonObject.put("open_id", openid);
        jsonObject.put("session_key", json.get("session_key"));

        return jsonObject;
    }
}
