package com.eim.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@ApiModel(value = "ShareInfo对象", description = "分享对象-ShareInfo")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShareInfo {
    private int shareId;
    private String openid;
    private String name;
    private int type;
    private String code;
    private String shareTitle;
    private String shareWord;
    private String sharePic;
    private Date time;
    private int totalNum;
    private String introduce;
    private String shareUrl;

    public ShareInfo(String openid, String name, String introduce, int type, String code, String sharePic, String shareWord, String shareTitle) {
        this.openid = openid;
        this.name = name;
        this.introduce = introduce;
        this.type = type;
        this.code = code;
        this.sharePic = sharePic;
        this.shareWord = shareWord;
        this.shareTitle = shareTitle;
    }
}
