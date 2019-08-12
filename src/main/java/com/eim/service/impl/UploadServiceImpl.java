package com.eim.service.impl;

import com.eim.service.UploadService;
import com.eim.util.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String uplodaImg(String base64, String saveUrl, String uploadUrl) {
        if (null != base64) {
            MultipartFile file = UploadUtil.base64ToMultipart(base64);
            if (!file.isEmpty()) {
                String url = UploadUtil.uploadImg(file, uploadUrl, saveUrl);
                return url;
            }
        }
        return null;
    }

    @Override
    public String reSizeImg(String base64, String baseUrl) throws IOException {

        if (base64.contains("data:image")) {
            //上传图片
            String img = uplodaImg(base64, "/pic/share_pic/", baseUrl + "/pic/share_pic/");
            File originalImage = new File(baseUrl + img);
            //对图片进行压缩
            //ImageUtil.resize(originalImage, originalImage, 50, 1f);
            return img;
        }
        return null;

    }
}
