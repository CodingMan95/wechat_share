package com.eim.service;

import java.io.IOException;

public interface UploadService {

    /**
     * 上传图片
     */
    String uplodaImg(String base64, String saveUrl, String uploadUrl);

    String reSizeImg(String base64, String baseUrl) throws IOException;
}
