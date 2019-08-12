package com.eim.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.eim.entity.ShareInfo;
import com.eim.kit.ConstantKit;
import com.eim.model.ResultTemplate;
import com.eim.service.ShareInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "访问次数管理")
@RestController
public class TotalController {
    @Autowired
    private ShareInfoService shareInfoService;

    @ApiOperation("页面访问一次，次数+1")
    @GetMapping("addOne.do")
    public ResultTemplate addOne(@RequestParam int shareId) {
        if (shareId == 0) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
        }
        ShareInfo share = shareInfoService.shareNum(shareId);

        if (null != share) {
            boolean add = shareInfoService.update(new UpdateWrapper<ShareInfo>().set("total_num", share.getTotalNum() + 1).eq("share_id", shareId));
            if (add) {
                return ResultTemplate.success();
            }
        }
        return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.FAIL);
    }

}
