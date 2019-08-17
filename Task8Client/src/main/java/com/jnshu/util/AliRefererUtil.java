package com.jnshu.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketReferer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

/**防盗链
 * */

@Component
public class AliRefererUtil {
    private static final Logger logger= LogManager.getLogger(AliRefererUtil.class);
    /**防盗链
     *#Endpoint以杭州为例，其它Region请按实际情况填写。
     * oss.endpoint=http://oss-cn-shanghai.aliyuncs.com
     * #阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     * oss.accessKeyId=LTAIX0q3dXdy0Bpw
     * oss.accessKeySecret=tBWa4Mo3d4IFS0ty31Eojn4GVP0nYT
     * oss.bucketName=jnshu-task7
     * */
    static String endpoint="http://oss-cn-shanghai.aliyuncs.com";
    static String accessKeyId="LTAIX0q3dXdy0Bpw";
    static String accessKeySecret="tBWa4Mo3d4IFS0ty31Eojn4GVP0nYT";
    static String bucketName="jnshu-task7";

    //创建referer白名单
    public void setReferer(List<String> refererList,boolean referer){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        //   List<String> refererList = new ArrayList<String>();
        //   // 添加Referer白名单。Referer参数支持通配符星号（*）和问号（？）。
        //   refererList.add("http://www.aliyun.com");
        //   refererList.add("http://www.*.com");
        //   refererList.add("http://www.?.aliyuncs.com");
        // 设置存储空间Referer列表。设为true表示Referer字段允许为空。
        BucketReferer br = new BucketReferer(referer, refererList);
        ossClient.setBucketReferer(bucketName, br);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //查找referer白名单
    public void getReferer(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        // 获取存储空间Referer白名单列表。
        BucketReferer br = ossClient.getBucketReferer(bucketName);
        List<String> refererList = br.getRefererList();
        for (String referer : refererList) {
            System.out.println(referer);
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //删除白名单referer
    public void deleteReferer(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        // 防盗链不能直接清空，需要新建一个允许空Referer的规则来覆盖之前的规则。
        BucketReferer br = new BucketReferer();
        ossClient.setBucketReferer(bucketName, br);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
