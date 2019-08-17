package com.jnshu.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;

/**缩略图
 * */

@Component
public class AliPictureUtil {
    private static final Logger logger= LogManager.getLogger(AliPictureUtil.class);
    /**
     *#Endpoint以杭州为例，其它Region请按实际情况填写。
     * oss.endpoint=http://oss-cn-shanghai.aliyuncs.com
     * #阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     * oss.accessKeyId=LTAIX0q3dXdy0Bpw
     * oss.accessKeySecret=tBWa4Mo3d4IFS0ty31Eojn4GVP0nYT
     * oss.bucketName=jnshu-task7
     */
    private static String endpoint="http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId="LTAIX0q3dXdy0Bpw";
    private static String accessKeySecret="tBWa4Mo3d4IFS0ty31Eojn4GVP0nYT";
    private static String bucketName="jnshu-task7";
    /**
     * 第一种自定义缩略图
     * 缩略图  设置缩略高度、宽度、模式
     *
     * @param oriURL 原始URL
     * @param height 高度
     * @param width  宽度
     * @param model  模式  lfit：等比缩放，限制在指定w与h的矩形内的最大图片。
     *
     */
    public String thumbnailURL(String oriURL, int height, int width, String model) {
        logger.debug("开始生成thumbnail, 原始链接: " + oriURL);
//        把URL中大写字母准换为小写字母，是否以http开头
        if (oriURL.toLowerCase().startsWith("http:")) {
            String oriURL_s = oriURL.split("\\?")[0];
            logger.debug("分割后的oriURL[0]: " + oriURL_s);
            String begin_thumbnailURL = oriURL_s + "?x-oss-process=image/resize";
            logger.debug("begin_thumbnailURL: " + begin_thumbnailURL);
            StringBuilder result = new StringBuilder();
            result.append(begin_thumbnailURL);
            result.append(",m_");
            result.append(model);
            result.append(",h_");
            result.append(String.valueOf(height));
            result.append(",w_");
            result.append(String.valueOf(width));
            return result.toString();
        } else {
            logger.debug("图片链接错误");
            return oriURL;
        }

    }

    //传入想修改的图片的对象名称
    public void setPicture(String objectName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId, accessKeySecret);
        // 缩放
        String style = "image/resize,m_fixed,w_100,h_100";
        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        //这里给下载的缩略图进行重命名
        ossClient.getObject(request, new File("example-resize.jpg"));
        // 裁剪
//        style = "image/crop,w_100,h_100,x_100,y_100,r_1";
//        request = new GetObjectRequest(pictureBean.getBucketName(), objectName);
//        request.setProcess(style);
//        ossClient.getObject(request, new File("example-crop.jpg"));
//        // 旋转
//        style = "image/rotate,90";
//        request = new GetObjectRequest(pictureBean.getBucketName(), objectName);
//        request.setProcess(style);
//        ossClient.getObject(request, new File("example-rotate.jpg"));
//        // 锐化
//        style = "image/sharpen,100";
//        request = new GetObjectRequest(pictureBean.getBucketName(), objectName);
//        request.setProcess(style);
//        ossClient.getObject(request, new File("example-sharpen.jpg"));
//        // 水印
//        style = "image/watermark,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ";
//        request = new GetObjectRequest(pictureBean.getBucketName(), objectName);
//        request.setProcess(style);
//        ossClient.getObject(request, new File("example-watermark.jpg"));
//        // 格式转换
//        style = "image/format,png";
//        request = new GetObjectRequest(pictureBean.getBucketName(), objectName);
//        request.setProcess(style);
//        ossClient.getObject(request, new File("example-format.png"));
//        // 获取图片信息
//        style = "image/info";
//        request = new GetObjectRequest(pictureBean.getBucketName(), objectName);
//        request.setProcess(style);
//        ossClient.getObject(request, new File("example-info.txt"));
        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
