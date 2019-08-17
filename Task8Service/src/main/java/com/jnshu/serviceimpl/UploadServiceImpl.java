package com.jnshu.serviceimpl;

import com.aliyun.oss.model.OSSObjectSummary;
import com.jnshu.service.UploadService;
import com.jnshu.util.AliOssUtil;
import com.jnshu.util.AliPictureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service("UploadServiceImpl")
public class UploadServiceImpl implements UploadService {
    @Autowired
    AliOssUtil ossUtil;
    @Autowired
    AliPictureUtil pictureUtil;

    @Override
    public String uploadFile(MultipartFile file) {
        return ossUtil.upload(file);
    }

    @Override
    public String uploadLocal(MultipartFile uploadFile) {
        return ossUtil.upload(uploadFile);
    }

    @Override
    public String upFile(String ObjectName, MultipartFile file) throws IOException {
        return ossUtil.upFile(ObjectName,file);
    }

    @Override
    public String thumbnailURL(String oriURL, int height, int width, String model) {
        return pictureUtil.thumbnailURL(oriURL,height,width,model);
    }


    @Override
    public void load(String fileName) throws IOException {
         ossUtil.load(fileName);
    }

    @Override
    public List<OSSObjectSummary> getObject() {
        return ossUtil.check();
    }

    @Override
    public boolean upLoadUrl(String Url, String fileName) {
        return ossUtil.upLoadUrl(Url, fileName);
    }



    @Override
    public boolean qiNiuToAli(String prefix, String delimiter) {
        return ossUtil.qiNiuToAli(prefix, delimiter);
    }
}