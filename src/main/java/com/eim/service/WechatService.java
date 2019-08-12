package com.eim.service;

import com.alibaba.fastjson.JSONObject;

public interface WechatService {
    /**
     * 根据code获取openid
     */
    JSONObject getOpenid(String code);
}
