package com.eim.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eim.entity.ShareInfo;
import com.eim.entity.User;
import com.eim.kit.ConstantKit;
import com.eim.model.ResultTemplate;
import com.eim.service.ShareInfoService;
import com.eim.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(tags = "分享管理")
@RestController
@RequestMapping("/share/")
@Transactional
public class ShareController {
    @Autowired
    private ShareInfoService shareInfoService;
    @Autowired
    private UserService userService;

    @ApiOperation("新建、编辑分享")
    @PostMapping("add.do")
    public ResultTemplate add(@RequestBody ShareInfo share, HttpServletRequest request) {

        boolean add = shareInfoService.add(share, request);
        if (add) {
            return ResultTemplate.success();
        }
        return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
    }

    @ApiOperation("根据openid获取自己的分享列表")
    @GetMapping("list.do")
    public ResultTemplate list(@RequestParam String openid, HttpServletRequest request) {
        if (null == openid) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
        }

        List<ShareInfo> list = shareInfoService.listShare(openid, request);

        return ResultTemplate.success(list);
    }

    @ApiOperation("根据openid获取自己的分享列表")
    @GetMapping("test.do")
    public ResultTemplate test(@RequestParam String openid) {

        User user = new User(openid, new Date());
        userService.save(user);

        int count = userService.count(new QueryWrapper<User>().eq("openid", openid));
        System.out.println(count);
        return ResultTemplate.success();
    }

    @ApiOperation("根据shareId获取分享详情")
    @PostMapping("shareDetail.do")
    public ResultTemplate shareDetail(@RequestParam int shareId) {
        if (shareId == 0) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
        }
        ShareInfo info = shareInfoService.getOne(new QueryWrapper<ShareInfo>().eq("share_id", shareId));
        return ResultTemplate.success(info);
    }

    @ApiOperation("根据shareId删除某一指定分享")
    @PostMapping("delete.do")
    public ResultTemplate delete(@RequestParam int shareId) {
        if (shareId == 0) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
        }
        boolean remove = shareInfoService.remove(new QueryWrapper<ShareInfo>().eq("share_id", shareId));
        if (remove) {
            return ResultTemplate.success();
        }
        return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.FAIL);
    }

}
