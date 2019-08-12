package com.eim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eim.entity.ShareInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShareInfoService extends IService<ShareInfo> {

    /**
     * 新建分享
     */
    boolean add(ShareInfo info, HttpServletRequest request);

    List<ShareInfo> listShare(String openid, HttpServletRequest request);

    ShareInfo shareNum(int shareId);

}
