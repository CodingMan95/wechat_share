package com.eim.controller;

import com.eim.kit.ConstantKit;
import com.eim.model.ResultTemplate;
import com.eim.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(tags = "图片管理")
@RestController
public class ImgController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation("对图片尺寸进行压缩")
    @PostMapping("reSize.do")
    public ResultTemplate reSize(@RequestParam String base64, HttpServletRequest request) throws IOException {
        if (null == base64) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NO_PARAMETER);
        }
        String picUrl = request.getSession().getServletContext().getRealPath("");
        picUrl = picUrl.replaceAll("\\\\", "/");
        String sizeImg = uploadService.reSizeImg(base64, picUrl);
        if (null == sizeImg) {
            return ResultTemplate.error(ConstantKit.BAD_REQUEST, ConstantKit.NOT_BASE64);
        }
        return ResultTemplate.success(sizeImg);
    }
}