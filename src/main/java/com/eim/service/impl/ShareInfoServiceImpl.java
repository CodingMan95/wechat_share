package com.eim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eim.entity.ShareInfo;
import com.eim.entity.User;
import com.eim.exception.BusinessException;
import com.eim.kit.ConstantKit;
import com.eim.mapper.ShareInfoMapper;
import com.eim.service.ShareInfoService;
import com.eim.service.UploadService;
import com.eim.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ShareInfoServiceImpl extends ServiceImpl<ShareInfoMapper, ShareInfo> implements ShareInfoService {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ShareInfoMapper shareInfoMapper;
    @Autowired
    private UserService userService;

    @Override
    public boolean add(ShareInfo info, HttpServletRequest request) {
        //缺少参数
        if (info.getName().isEmpty() || info.getIntroduce().isEmpty() || info.getOpenid().isEmpty() || info.getCode().isEmpty()) {
            return false;
        }

        if (info.getTotalNum() == 0) {
            info.setTotalNum(0);
        }

        info.setTime(new Date());
        String picUrl = request.getSession().getServletContext().getRealPath("/pic");
        //上传二维码并保存
        if (info.getCode().contains("data:image")) {
            String codeUrl = uploadService.uplodaImg(info.getCode(), "/pic/code/", picUrl + "/code/");
            info.setCode(codeUrl);
        }

        //有分享图
        if ("" != info.getSharePic()) {
            if (info.getSharePic().contains("data:image")) {
                //对图片进行压缩
                /*String baseUrl = request.getSession().getServletContext().getRealPath("");
                baseUrl = baseUrl.replaceAll("\\\\", "/");
                try {
                    String sizeImg = uploadService.reSizeImg(info.getSharePic(), baseUrl);
                    info.setSharePic(sizeImg);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                String sharePicUrl = uploadService.uplodaImg(info.getSharePic(), "/pic/share_pic/", picUrl + "/share_pic/");
                info.setSharePic(sharePicUrl);
            }
        } else {
            //没有上传分享图，则设置一张默认分享图
            info.setSharePic(ConstantKit.DEFAULT_PIC);
        }

        //没有分享标题,则默认使用小程序/公众号名称
        if ("" == info.getShareTitle() || "".equals(info.getShareTitle())) {
            info.setShareTitle(info.getName());
        }

        //没有分享语,则默认使用小程序/公众号介绍
        if ("" == info.getShareWord() || "".equals(info.getShareWord())) {
            info.setShareWord(info.getIntroduce());
        }

        String code = info.getCode();
        if (code.contains("http")) {
            info.setCode(code.substring(code.lastIndexOf("pic") - 1));
        }

        String sharePic = info.getSharePic();
        if (sharePic.contains("http")) {
            info.setSharePic(sharePic.substring(sharePic.indexOf("pic") - 1));
        }

        boolean status;
        if (info.getShareId() == 0) {
            //保存分享信息
            status = shareInfoMapper.addShareInfo(info);
            //生成链接并更新
            update(new UpdateWrapper<ShareInfo>().set("share_url", ConstantKit.SHARE_URL + info.getShareId()).eq("share_id", info.getShareId()));
        } else {
            //ShareInfo shareInfo = getOne(new QueryWrapper<ShareInfo>().select("code", "share_pic"));
            status = shareInfoMapper.updateShareInfo(info);
        }
        return status;
    }

    @Override
    public List<ShareInfo> listShare(String openid, HttpServletRequest request) {

        int count = userService.count(new QueryWrapper<User>().eq("openid", openid));
        List<ShareInfo> list = shareInfoMapper.listShare(openid);
        if (list.isEmpty() && count == 0) {
            ShareInfo info = new ShareInfo(openid, "互动工场服务号（示意）", "请不要关注我们的公众号，没有什么内容。关注我们开发的产品就好", 0, "/pic/code/0.jpg", "/pic/share_pic/0.png", "请不要关注我们的公众号，没有什么内容。关注我们开发的产品就好", "互动工场服务号（示意）");
            add(info, request);
            ShareInfo info1 = new ShareInfo(openid, "小保证（示意）", "你是否经常被朋友放鸽子？你是否经常被爽约？你是否经常遇到明明说好却又反悔的事？于是“小保证”小程序诞生了。", 1, "/pic/code/1.jpg", "/pic/share_pic/0.png", "你是否经常被朋友放鸽子？你是否经常被爽约？你是否经常遇到明明说好却又反悔的事？于是“小保证”小程序诞生了。", "小保证（示意）");
            add(info1, request);

            User user = new User(openid, new Date());
            userService.save(user);
        }
        List<ShareInfo> list1 = shareInfoMapper.listShare(openid);

        return list1;
    }

    @Override
    public ShareInfo shareNum(int shareId) {
        return shareInfoMapper.shareNum(shareId);
    }
}
