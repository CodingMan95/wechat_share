package com.eim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eim.entity.ShareInfo;

import java.util.List;

public interface ShareInfoMapper extends BaseMapper<ShareInfo> {
    boolean updateShareInfo(ShareInfo info);

    boolean addShareInfo(ShareInfo info);

    List<ShareInfo> listShare(String openid);

    ShareInfo shareNum(int shareId);

}
