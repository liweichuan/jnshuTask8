package com.jnshu.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;


/**阿里对象存储，上传下载
 * */

@Component
public class AliOssUtil {
    private static final Logger logger= LogManager.getLogger(AliOssUtil.class);
    /**
     * oss属性
     *#Endpoint以杭州为例，其它Region请按实际情况填写。
     * oss.endpoint=http://oss-cn-shanghai.aliyuncs.com
     * #阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     * oss.accessKeyId=LTAIX0q3dXdy0Bpw
     * oss.accessKeySecret=tBWa4Mo3d4IFS0ty31Eojn4GVP0nYT
     * oss.bucketName=jnshu-task7
     * #图片存储类型
     * oss.contentype=image/jpeg
     */
    private static String endpoint="http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId="LTAIX0q3dXdy0Bpw";
    private static String accessKeySecret="tBWa4Mo3d4IFS0ty31Eojn4GVP0nYT";
    private static String bucketName="jnshu-task7";
    private static String contentype="image/jpeg";


    @Autowired
    QiNiuOssUtil qiNiuOssUtil;
    // 创建OSSClient实例。
    //    OSS ossClient = new OSSClientBuilder().build(ossBean.getEndpoint(),ossBean.getAccessKeyId(),ossBean.getAccessKeySecret());

    /**
     * 通过URL上传
     *
     * @param Url
     * @param fileName
     */
    public boolean upLoadUrl(String Url, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        try {
            //打开到此URL的连接，并返回一个InputStream以便从该连接读取。这个方法是:openConnection().getInputStream()
            InputStream inputStream = new URL(Url).openStream();
            assert ossClient != null;
            //将文件从InputStream实例上传到空间中。它覆盖了现有的一个，命名空间必须存在。
            ossClient.putObject(bucketName,fileName,inputStream);
            ossClient.shutdown();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //multipartFile格式上传
    public String upload(MultipartFile uploadFile){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        // 上传文件流。以文件流的方式上传
        try {
            InputStream inputStream = uploadFile.getInputStream();
            // 文件名
            String fileName=uploadFile.getOriginalFilename();
            logger.info("上传文件名：" + fileName);
            // InputStream 格式上传 必须填写文件类型
            //Object通过InputStream的形式上传到OSS中。每上传一个Object，
            // 都需要指定和Object关联的ObjectMetadata。ObjectMetaData是用户对该object的描述，
            // 由一系列name-value对组成；其中ContentLength是必须设置的，以便SDK可以正确识别上传Object的大小。
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度(必须)
            metadata.setContentLength(inputStream.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。
            // 如果用户没有指定则根据Key或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(uploadFile.getContentType());
            //获取上传结果
            PutObjectResult putObjectResult=ossClient.putObject(bucketName, fileName, inputStream,metadata);
            String result=putObjectResult.getETag();
            logger.info("上传结果：" + result);
            //设置URL过期时间为十年（私有最多3600s）
            Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
            //获取图片上传链接
            String url=ossClient.generatePresignedUrl(bucketName,fileName,expiration).toString();
            // 关闭client
            ossClient.shutdown();
            return url;
        } catch (Exception e) {
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            return "";
        }
    }


    /**本地文件上传MultipartFile文件上传
     * */
    public String upFile(String ObjectName, MultipartFile file) throws IOException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        if (ossClient.doesBucketExist(bucketName)) {
            logger.error("您已经创建Bucket：" + bucketName + "。");
        } else {
            logger.error("您的Bucket不存在，创建Bucket：" + bucketName + "。");
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            ossClient.createBucket(bucketName);
        }
        // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        BucketInfo info = ossClient.getBucketInfo(bucketName);
        logger.error("Bucket " + bucketName + "的信息如下：");
        logger.error("\t数据中心：" + info.getBucket().getLocation());
        logger.error("\t创建时间：" + info.getBucket().getCreationDate());
        logger.error("\t用户标志：" + info.getBucket().getOwner());

        //设置下载时文件类型
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentype);
        //上传文件，第一参数，存储空间，第二个参数储存在Oss上的位置（文件或对象），第三个参数是传入的文件
        ossClient.putObject(bucketName, ObjectName, new ByteArrayInputStream(file.getBytes()), objectMetadata);
        //设置URL过期时间为十年（私有最多3600s）
        Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
        //获取图片上传链接
        String url=ossClient.generatePresignedUrl(bucketName,ObjectName,expiration).toString();
        // 关闭client
        ossClient.shutdown();
        return url;
    }

    /**File 文件上传
     * */
    public String upFile(String ObjectName,File file) throws IOException {
        logger.error("阿里上传开始");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        if (ossClient.doesBucketExist(bucketName)) {
            logger.error("您已经创建Bucket：" + bucketName + "。");
        } else {
            logger.error("您的Bucket不存在，创建Bucket：" + bucketName + "。");
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            ossClient.createBucket(bucketName);
        }
        // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        BucketInfo info = ossClient.getBucketInfo(bucketName);
        logger.error("Bucket " + bucketName + "的信息如下：");
        logger.error("\t数据中心：" + info.getBucket().getLocation());
        logger.error("\t创建时间：" + info.getBucket().getCreationDate());
        logger.error("\t用户标志：" + info.getBucket().getOwner());

        //设置下载时文件类型
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentype);
        //上传文件，第一参数，存储空间，第二个参数储存在Oss上的位置（文件或对象），第三个参数是传入的文件
        ossClient.putObject(bucketName,ObjectName, file);
        //设置URL过期时间为十年（私有最多3600s）
        Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
        //获取图片上传链接
        String url=ossClient.generatePresignedUrl(bucketName,ObjectName,expiration).toString();
        // 关闭client
        ossClient.shutdown();
        logger.error("阿里上传完成");
        return url;
    }

    /**查看储存文件
     *
     * @return*/
    public List<OSSObjectSummary> check(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
        // 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
        System.out.println("您有以下Object：");
        for (OSSObjectSummary object : objectSummary) {
            logger.error("\t" + object.getKey());
        }
        ossClient.shutdown();
        return objectSummary;
    }
    /**下载图片
     * */
    public void load(String ObjectName) throws IOException {
        logger.error("阿里下载开始");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        GetObjectRequest request = new GetObjectRequest(bucketName, ObjectName);
        ossClient.getObject(request, new File(ObjectName));
        logger.error("阿里下载完成");
        ossClient.shutdown();
    }

    //MultipartFile 转换成file类型
    private File mul2File(MultipartFile file) {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        return f;
    }


    /**删除图片
     * */
    public void delete(String ObjectName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
        ossClient.deleteObject(bucketName,ObjectName);
        logger.error("删除Object：" + ObjectName + "成功。");
        ossClient.shutdown();
    }
    public boolean qiNiuToAli(String prefix, String delimiter) {
        // 获取七牛云中文件列表（key 文件名）
        try {
            List <String> key =qiNiuOssUtil.fileList(prefix,delimiter);
            for (String i : key) {
                //获取七牛云下载URL
                logger.info("七牛云下载-");
                //        filePath = "C:/task/Task7/";
                String Url = qiNiuOssUtil.downLoad(i,i,"C:/task/Task7/");
                logger.info("阿里云上传-");
                this.upLoadUrl(Url, i);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}