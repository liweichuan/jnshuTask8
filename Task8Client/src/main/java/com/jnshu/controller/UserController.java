package com.jnshu.controller;

import com.aliyuncs.exceptions.ClientException;
import com.jnshu.pojo.Result;
import com.jnshu.pojo.User;
import com.jnshu.service.MailService;
import com.jnshu.service.MessageService;
import com.jnshu.service.RedisService;
import com.jnshu.service.UserService;
import com.jnshu.util.DesUtil;
import com.jnshu.util.IntegerCastUtil;
import com.jnshu.util.MessageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import com.jnshu.tool.aliMailUtil.AliMailUtil;


@Controller
public class UserController {
    private static final Logger logger= LogManager.getLogger(UserController.class);
    //获取配置文件
    private ApplicationContext applicationContext=new ClassPathXmlApplicationContext("rmiClient.xml");
    //客户端进行实例化
    private UserService userService=applicationContext.getBean("userClient", UserService.class);
    private MailService aliMailUtil=applicationContext.getBean("mailClient", MailService.class);
    private MessageService aliSmsUtil=applicationContext.getBean("messageClient", MessageService.class);
    private RedisService redisUtil=applicationContext.getBean("redisClient", RedisService.class);
//    @Resource
//    private UserService userService;
//    @Resource
//    private MailService aliMailUtil;
//    @Resource
//    private MessageService aliSmsUtil;

    @Autowired
    DesUtil desUtil;

    @Autowired
    MessageUtil messageUtil;

    @Autowired
    IntegerCastUtil integerCastUtil;


    //这是get方法返回注册页面请求 默认手机号验证码注册
    @RequestMapping(value = "/user/register/0",method = RequestMethod.GET)
    public String register(){
        //user.type=0
        return "register";
    }
    //注册按钮点击之前会点击一下发送验证码的按钮,不会返回视图  这里获取验证码不能添加到数据库，不然就相当于注册了
    //所以这里采用缓存存验证码，key是用户名 value是有两种 1.email:1946931904@qq.com;123456
    //2.phone:13699670397;123456   之后在注册的时候进行比对，从缓存中取数据，注册成功了就存在数据库
   //这里有更简单的方式，把整个user存进缓存

    @RequestMapping(value = "/user/message",method = RequestMethod.POST)
    @ResponseBody
    public void sendMessage(String phone) throws ClientException {
        //调用工具类生成验证码
        String message=messageUtil.getMesgCode();
        logger.error(message);
        logger.error(phone);
        //将验证码存储在redis
        if (redisUtil==null){
            logger.error("redis实现类为空");
        }
        boolean result=redisUtil.add(phone,message);
        logger.error(result==true?"将验证码存入缓存成功":"将验证码存入缓存失败");
        aliSmsUtil.sendMesg(phone,message);
    }

    //使用get请求返回邮箱验证码注册页面
    @RequestMapping(value = "/user/register/1",method = RequestMethod.GET)
    public String emailRegister(){
        //user.type=1
        return "emailRegister";
    }

    //发送邮件验证码
    @RequestMapping(value = "/user/email",method = RequestMethod.POST)
    @ResponseBody
    public void sendEmail(String email) throws ClientException, IOException {
        //调用工具类生成验证码
        String message=messageUtil.getMesgCode();
        logger.error(message);
        logger.error(email);
        //将验证码存储在redis
        boolean result=redisUtil.add(email,message);
        logger.error(result==true?"将验证码存入缓存成功":"将验证码存入缓存失败");
        aliMailUtil.sendMail(email,message);
    }


    //post新增用户请求
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public String register(@ModelAttribute User user, Model model){
        Result result=new Result();
        if (user.getPhone()!=null){
            user.setType(0);//设置验证码类型为手机
        }else if (user.getEmail()!=null){
            user.setType(1);//设置验证码类型为邮箱
        }
        logger.error(user);
        Long num = userService.register(user);
        if (num==0) {
            //返回0说明是用户名注册时输入为空,或已经存在
            result.setCode("401");
            result.setMessage("用户名已存在,或用户名为空");
            result.setData(null);
            model.addAttribute("result1",result);
            return "message";                  //为空
        }else if (num==1){
            //返回值为1说明密码为空
            result.setCode("402");
            result.setMessage("注册失败，密码不能为空");
            result.setData(null);
            model.addAttribute("result1",result);
            return "message";
        }else if (num==2){
            //返回值为2说明验证码格式不对，长度不为6
            result.setCode("403");
            result.setMessage("注册失败，验证码格式错误");
            result.setData(null);
            model.addAttribute("result1",result);
            return "message";
        }else if (num==3){
            //返回值为3说明验证码错误
            result.setCode("403");
            result.setMessage("注册失败，验证码输入错误");
            result.setData(null);
            model.addAttribute("result1",result);
            return "message";
        }else if (num==4){
            //返回值为4说明注册成功
            logger.info("注册成功，返回登录界面");
            return "registerSuccess";
        }
        //以上情况都不在内，出现了未知的错误
        logger.error("出现了未知的错误");
        return "error";
    }
    //这是get方法返回登录页面请求
    @RequestMapping(value = "/user/login/0",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    //这是登录请求
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public String checkLogin(Model model, @Param("loginname")String loginname, @Param("password")String password,
                             HttpServletRequest request, HttpServletResponse response){
        logger.error(loginname);//没有传进来
        Result result =userService.checkLogin(loginname,password);
        User user= (User) result.getData();
        if (user!=null){
            logger.error(loginname,password);
            logger.error("登录成功");
            //使用DES对用户ID和登录时间进行加密，生成token，放入cookie中
            long id=user.getId();
            String token=id+"/"+System.currentTimeMillis();//id和当前时间戳进行连接，中间加一个/
            try {
                token=desUtil.encrypt(token);//加密字符串
                Cookie cookie=new Cookie("token",token);//给cookie里面加一个键值对
                cookie.setMaxAge(60*3);//设置cookie的最大年龄，以秒为单位，这里就设置成180秒
                cookie.setPath("/");//官方的注释是：指定cookie的路径，客户端将cookie返回到该目录下
                //设置cookie的有效路径，比如把cookie的有效路径设置成“/xdp”,那么浏览器访问“xdp”目录下的web资源的时候也都会带上cookie
                logger.error("cookie的有效路径是"+cookie.getPath()+"cookie的有效时间是"+cookie.getMaxAge()+
                        "cookie生成的名字为"+cookie.getName());//将生成的cookie发送给浏览器客户端
                response.addCookie(cookie);//将指定的cookie添加到响应中
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "loginSuccess";//验证通过
        }else {
            logger.debug(result.getMessage());//这里就是登录验证不通过，具体原因可以看message
            model.addAttribute("result1",result);
            return "message";  //验证不通过，又跳转到登录页面
        }
    }
    //退出方法请求
    @RequestMapping(value ="/user/loginOut",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies=request.getCookies();
        for (Cookie cookie:cookies){                //这里就是把cookie中的所有数据都来出来比对
            if ("token".equals(cookie.getName())){  //当我们找到了要注销的账号的信息，就把这个账号的信息进行修改
                cookie=new Cookie("token",null);//token的值设为null
                cookie.setMaxAge(0);               //这个cookie的是、有效时间设为0，（就是设为无效）
                cookie.setPath("/");
                response.addCookie(cookie);         //然后把这个cookie加到所有的cookie中存储
                return "redirect:/user/login/0";//重定向到登录页面
            }
        }
        return "redirect:/user/login/0";//重定向到登录页面
    }
}
