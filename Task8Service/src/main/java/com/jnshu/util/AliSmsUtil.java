package com.jnshu.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**阿里短信验证码发送工具类
 * */

//这里我们把手机号和验证码设为变量,引入phone和message
@Component
public class AliSmsUtil {
    private static final Logger logger= LogManager.getLogger(AliSmsUtil.class);
    /**
     * mesg.acceKeyId=LTAIfUGUaJwt8k2u
     * mesg.accessKeySecret=EpOLzCEzG85Kgc5I225Rr1Q4zU1XC9
     * mesg.SignName=java\u5b66\u4e60\u6210\u957f
     * mesg.TemplateCode=SMS_170841648
     * mesg.ConnectTimeout=1000
     * mesg.ReadTimeout=1000
     * */

    //短信API产品域名（接口地址固定，无需修改）
    private static String domain="dysmsapi.aliyuncs.com";
    private static String accessKeyId="LTAIfUGUaJwt8k2u";
    private static String accessKeySecret="EpOLzCEzG85Kgc5I225Rr1Q4zU1XC9";
    private static String SignName="java\u5b66\u4e60\u6210\u957f";
    private static String TemplateCode="SMS_170841648";
    private static String ConnectTimeout="1000";
    private static String ReadTimeout="1000";


    public boolean sendMesg(String phone,String message) throws ClientException, ServerException {
        System.setProperty("sun.net.client.defaultConnectTimeout",ConnectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout",ReadTimeout);

        //初始化acsClient,暂不支持region化
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //必填:短信签名-可在短信控制台中找到
        request.putQueryParameter("SignName",SignName);
        //必填:短信模板-可在短信控制台中找到
        request.putQueryParameter("TemplateCode",TemplateCode);

        request.putQueryParameter("TemplateParam", "{\"code\":"+message+"}");

        try {
        CommonResponse response = client.getCommonResponse(request);
        logger.error(response.getData());
        return true;
       } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}