package com.jnshu.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**七牛云对象储存
 * */

@Component
public class QiNiuOssUtil {
    private static final Logger logger= LogManager.getLogger(QiNiuOssUtil.class);
    /**
     *#测试域名
     * qiniu.endpoint=http://pvlap0210.bkt.clouddn.com
     * #密钥
     * qiniu.accessKeyId=AaoYDkw7DwRw0i08Z0fKfoM0utpWyXYymOxad90l
     * qiniu.accessKeySecret=Va0RrU9nulAJw4bfCqCt37XpQq6SxhjDyhfHlHD3
     * #空间名
     * qiniu.bucketName=lianchenyouxu
     * */
    private static String endpoint="http://pvlap0210.bkt.clouddn.com";
    private static String accessKeyId="AaoYDkw7DwRw0i08Z0fKfoM0utpWyXYymOxad90l";
    private static String accessKeySecret="Va0RrU9nulAJw4bfCqCt37XpQq6SxhjDyhfHlHD3";
    private static String bucketName="lianchenyouxu";


    //最简单的上传凭证只需要AccessKey，SecretKey和Bucket就可以。
    //url为外链地址加对象名
    public void upload(String localFilePath,String key) throws IOException {
            logger.error("七牛上传开始");
            Configuration cfg = new Configuration(Zone.zone0()); //zong1() 代表华北地区
            UploadManager uploadManager = new UploadManager(cfg);

            String accessKey = "AaoYDkw7DwRw0i08Z0fKfoM0utpWyXYymOxad90l";//AccessKey的值
            String secretKey = "Va0RrU9nulAJw4bfCqCt37XpQq6SxhjDyhfHlHD3";//SecretKey的值
            String bucket = "lianchenyouxu";      //存储空间名
//            localFilePath = "C:\\Users\\liweichuan\\Pictures\\Saved Pictures\\10.jpg";//上传图片路径

//            key = "10.jpg";               //在七牛云中图片的命名
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                logger.error("七牛上传失败");
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
            logger.error("七牛上传完成");
    }

    //下载  公有空间
    //对于公开空间，其访问的链接主要是将空间绑定的域名（可以是七牛空间的默认域名或者是绑定的自定义域名）
    // 拼接上空间里面的文件名即可访问，标准情况下需要在拼接链接之前，将文件名进行urlencode以兼容不同的字符。
    public String downLoad(String fileName,String localFile,String filePath) throws UnsupportedEncodingException {
        //获取下载连接
        logger.error("七牛下载开始");
        String domainOfBucket = endpoint;
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String  finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        logger.info("下载的链接是:"+finalUrl);

        String accessKey = accessKeyId;//AccessKey值
        String secretKey = accessKeySecret;//SecretKey值

        //密钥配置
        Auth auth = Auth.create(accessKey,secretKey);
        //准备开始下载，获取下载文件路径
        String downloadUrl = auth.privateDownloadUrl(finalUrl);
        //本地保存路径
//        filePath = "C:/task/Task7/";
        //通过发送Http get请求获取资源
        OkHttpClient client = new OkHttpClient();
        System.out.println(downloadUrl);
        Request req = new Request.Builder().url(downloadUrl).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if(resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                //读取字节输入流内容
                ByteArrayOutputStream writer = new ByteArrayOutputStream();
                byte[] buff = new byte[1024 * 2];
                int len = 0;
                try {
                    while((len = is.read(buff)) != -1) {
                        writer.write(buff, 0, len);
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] data= writer.toByteArray();
                //下载到本地
                File imgFile = new File(filePath + localFile);//下载到本地的图片命名
                FileOutputStream fops = new FileOutputStream(imgFile);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            logger.error("七牛下载失败");
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
        logger.error("七牛下载完成");
        return finalUrl;
    }


    //获取存储空间的文件列表

    public List<String>fileList(String prefix, String delimiter ) {

        //密钥配置
        Auth auth = Auth.create(accessKeyId,accessKeySecret);
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
//          prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
//          limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
//          delimiter = "";
        //列举空间文件列表
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucketName, prefix, limit, delimiter);
        List <String> keys = new ArrayList<String>();
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
//                  System.out.println(item.key);
//                  System.out.println(item.hash);
//                  System.out.println(item.fsize);
//                  System.out.println(item.mimeType);
//                  System.out.println(item.putTime);
//                  System.out.println(item.endUser);
                keys.add(item.key);
            }
        }
        return keys;
    }


    //删除空间中的文件
    public boolean del(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //密钥配置
        Auth auth = Auth.create(accessKeyId, accessKeySecret);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucketName, fileName);
            logger.error("删除文件");
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
    }

    private File mul2File(MultipartFile file) {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        return f;
    }

}