package com.jnshu.service;

import com.aliyun.oss.model.OSSObjectSummary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UploadService {
    //以文件流的方式上传文件
    String uploadFile(MultipartFile file);
    //以文件的绝对路径方式上传文件;
    String uploadLocal(MultipartFile uploadFile);
    //本地上传图片
    String upFile(String ObjectName, MultipartFile file) throws IOException;

    //自定义缩略图 下载
    String thumbnailURL(String oriURL, int height, int width, String model);

    // 下载
    void  load(String fileName) throws IOException;
    //列举文件
    List<OSSObjectSummary> getObject();
    //通过URL上传
    boolean upLoadUrl(String Url, String fileName);

    //文件迁移
    boolean qiNiuToAli(String prefix, String delimiter) throws UnsupportedEncodingException;
}
