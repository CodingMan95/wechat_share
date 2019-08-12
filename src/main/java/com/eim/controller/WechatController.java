package com.eim.controller;

import com.alibaba.fastjson.JSONObject;
import com.eim.config.WXConfig;
import com.eim.kit.ConstantKit;
import com.eim.model.ResultTemplate;
import com.eim.service.WechatService;
import com.eim.util.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "微信小程序授权管理")
@RestController
public class WechatController {
    @Autowired
    private WechatService wechatService;

    @ApiOperation("根据code获取openid")
    @PostMapping("/getOpenidByCode.do")
    public ResultTemplate getUserInfo(@RequestParam String code) {

        // 登录凭证不能为空
        if (code == null || code.length() == 0) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
        }
        JSONObject jsonObject = wechatService.getOpenid(code);

        return ResultTemplate.success(jsonObject);
    }

}
