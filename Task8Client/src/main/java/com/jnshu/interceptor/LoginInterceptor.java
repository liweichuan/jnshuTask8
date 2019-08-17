
package com.jnshu.interceptor;


import com.jnshu.service.UserService;
import com.jnshu.util.DesUtil;
import com.jnshu.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {//登录拦截器，用来拦截cookie中没有“token"的/u/profession请求

    @Autowired
    DesUtil desUtil;

    @Autowired
    UserService userService;

    /**
     * preHandle方法是进行处理器拦截用的，该方法将在Controller处理之前进行调用，
     * 当preHandle的返回值为false的时候整个请求就结束了。
     * 如果preHandle的返回值为true，则会继续执行postHandle和afterCompletion。
     */
    private  static final Logger logger= LogManager.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.error("拦截器被调用");
        //判断请求中是否携带了token令牌，同时对token令牌进行验证。
        Cookie[] cookies=httpServletRequest.getCookies();//返回一系列的数组，其中包含随客户端随此请求发送的所有cookie对象，如果没有就返回null
        if(cookies!=null&&cookies.length>0){//判断cookies数组不为空，其中至少有一个cookie
            logger.error("开始遍历cookie");
            for (Cookie cookie: cookies) {//开始遍历cookies
                //判断cookie是否存储了token
                if("token".equals(cookie.getName())){//判断是否有cookie的名称为“token”
                    //因为在登录的过程中，使用DES对用户ID和登录时间加密，生成Token。所以现在将token用DES解密
                    String token=desUtil.decrypt(cookie.getValue());//如果有名为“token”的cookie，取出它的值，进行解密，重新赋值给token
                    //将解密后的token值中的用户id提取出来
                    long id= StringUtil.tool(token);

//       JWT认证       DecodedJWT jwt= JwtUtil.parserJavaWebToken(cookie.getValue());
//                     Map<String, Claim>claimMap=jwt.getClaims();
//                     String name=claimMap.get("userName").asString();
//                     Date date=jwt.getExpiresAt();
//                     long time=date.getTime();
//                     boolean flag= time > System.currentTimeMillis();

                    if(userService.findById(id)!=null){//这里调用根据id查询user的方法，我们判断这个返回的结果不为空
                        //验证token有效，返回true继续向下执行
                        return true;        //返回true
                    }
//                     if(name!=null && flag){
//                         return true;
//                     }
                    else {//根据id查找的结果为空，那么
                        logger.error(id);
                        logger.error(userService.findById(id));
                        logger.error("验证失败，重新返回到登录界面");
                        String uri=httpServletRequest.getContextPath()+"/user/login/0";//重定向到登录界面并把这个值给uri
                        httpServletRequest.getRequestDispatcher(uri).forward(httpServletRequest,httpServletResponse);
                        return false;  //返回false
                    }

                }
            }
        }
        logger.error("cookies不存在");
        httpServletRequest.getRequestDispatcher("/user/login/0").forward(httpServletRequest,httpServletResponse);
        return false;
    }

    //  postHandler : 进入Handler方法之后，返回ModelAndView之前执行，使用场景从ModelAndView参数出发，
    // 比如，将公用的模型数据在这里传入到视图，也可以统一指定显示的视图等；
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //   afterHandler : 在执行Handler完成后执行此方法，使用于统一的异常处理，统一的日志处理等；该方法将在整个请求完成之后执行，主要作用是用于清理资源。
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}