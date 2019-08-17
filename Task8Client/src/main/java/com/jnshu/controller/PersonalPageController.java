package com.jnshu.controller;


import com.jnshu.pojo.User;
import com.jnshu.service.UploadService;
import com.jnshu.service.UserService;
import com.jnshu.util.CookieUtil;
import com.jnshu.util.DesUtil;
import com.jnshu.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/*个人信息编辑页面，上传图片
**/


@Controller
@RequestMapping("/u")
public class PersonalPageController {

    //获取配置文件
    ApplicationContext applicationContext=new ClassPathXmlApplicationContext("rmiClient.xml");
    //客户端进行实例化
    UserService userService=applicationContext.getBean("userClient", UserService.class);
    UploadService ossUtil=applicationContext.getBean("uploadClient", UploadService.class);
    private static final Logger logger= LogManager.getLogger(PersonalPageController.class);
    @Autowired
    DesUtil desUtil;

    @Autowired
    StringUtil stringUtil;


    long id;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String findUserById(Model model,HttpServletRequest request) throws Exception {
        //取出存储在cookie中的token
        Cookie[] cookies=request.getCookies();
        for (Cookie cookie:cookies){                //这里就是把cookie中的所有数据都来出来比对
            if ("token".equals(cookie.getName())){  //当我们找到了token，将token逆向解析，取出id
                String token=desUtil.decrypt(cookie.getValue());//解析出来的token是id+/+一个时间戳
                long id=stringUtil.tool(token); //这个id就是user的id，根据id查询user，返回个人信息
                List<User> users= Collections.singletonList(userService.findById(id));
                logger.error(users);
                model.addAttribute("user",users);
            }
        }
        return "user";//返回个人信息页面
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public String image() {
        return "upload";
    }

    /**
     * @return
     * @Description 上传头像
     * @Param
     **/
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ModelAndView springUpload(MultipartFile file, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        // 文件过大 报错
        if (file.getBytes().length > 1024 * 1024 * 2) {
            mv.setViewName("error");
            mv.addObject("msg", "图片过大");
            return mv;
        } else {
            // 获取用户id
            Cookie cookie = CookieUtil.getCookie("token", request);
            logger.error(cookie.getValue());
            String token=desUtil.decrypt(cookie.getValue());
            logger.error(token);
            String id=token.substring(0,token.indexOf("/"));//取出字符串中的下标0到“/”的下标
            User user = userService.findById(Long.valueOf(id));
            // 获取用户姓名，以姓名为oss存储文件名
            String name = user.getUsername();
            ossUtil.upFile("picture/"+name, file);
            String url = "https://jnshu-task7.oss-cn-shanghai.aliyuncs.com/picture/" + name;
            user.setPicture(url);
            user.setUpdate_time(System.currentTimeMillis());
            userService.updateUser(user);
            mv.setViewName("redirect:/u/user");
            return mv;
        }
    }

//
//    @RequestMapping(value = "/qiniutoali", method = RequestMethod.GET)
//    @ResponseBody
//    public void QiNiuToALi() throws UnsupportedEncodingException {
//          ossUtil.qiniuToali("","","test");
////        modelMap.addAttribute("dataMigration", "数据已从七牛云迁移到阿里云！");
//    }
}
