package com.jnshu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jnshu.pojo.Student;
import com.jnshu.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Controller
public class StudentController {
    /**
     * org.springframework.web.bind.annotation.RequestMapping注解
     * 用来映射请求的的URL和请求的方法等。用来映射"/方法"
     * 方法名只是一个普通方法。
     * 该方法返回一个包含视图路径或视图路径和模型的ModelAndView对象。
     * */
      //获取配置文件
    ApplicationContext applicationContext=new ClassPathXmlApplicationContext("rmiClient.xml");
    //客户端进行实例化
    StudentService studentService=applicationContext.getBean("studentClient", StudentService.class);

    //下面就使用这个实例化的对象进行调用方法
    private static final Logger logger= LogManager.getLogger(StudentController.class);

    /**返回值跳转到学生添加界面
     * */
    @RequestMapping(value = "/student/a",method = RequestMethod.GET)
    public String toAddStudent(){
        return "addStudent";
    }
    /**添加学生
     * */
    @RequestMapping(value = "/student",method = RequestMethod.POST)
    public String addStudent(Model model,HttpServletRequest request,
                             @Validated Student student, BindingResult bindingResult){
        if(student.getName().length()==0&& bindingResult.hasErrors()){
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors){
                logger.error(objectError.getDefaultMessage());
            }
            //将错误信息传至页面
            model.addAttribute("allErrors",allErrors);
            //出错则重新至商品更新页面
            logger.error(bindingResult);
            return "error";
        }

        logger.info(student);
        if (student!=null) {
            studentService.addStudent(student);
            model.addAttribute("student",student);
        }
        return "redirect:/student/list";
    }
    /**更新学生数据
     * */
    @RequestMapping(value = "/student/u/{student_id}",method = RequestMethod.GET)
    public String toUpdateStudent(Model model,@PathVariable Long student_id){
        model.addAttribute("student",studentService.findStudentById(student_id));
        return "editStudent";
    }
    /**
     * 更新学生数据
     */
    @RequestMapping(value = "/student/u",method = RequestMethod.POST)
    public String updateStudent(Model model,HttpServletRequest request,
                                @Validated Student student, BindingResult bindingResult){
        if(student.getName().length()==0&& bindingResult.hasErrors()){
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors){
                logger.error(objectError.getDefaultMessage());
            }
            //将错误信息传至页面
            model.addAttribute("allErrors",allErrors);
            //出错则重新至商品更新页面
            logger.error(bindingResult);
            return "error";
        }

        logger.error(student);
        if (studentService.updateStudentById(student)){
            student=studentService.findStudentByName(student.getName());
            logger.error(student);
            model.addAttribute("student",student);
            return "redirect:/student/list";
        }else {
            return "error";
        }
    }

    /**删除学生
     * */
    @RequestMapping(value = "/student/{name}",method = RequestMethod.DELETE)
    public String deleteStudent( @PathVariable String name,Model model)throws Exception{
        if (studentService.deleteStudentByName(name)){
            model.addAttribute("student",null);
        }else {
            return "error";
        }
        return "redirect:/student/list";
    }

    /**查询单个学生
     * */
    @RequestMapping(value = "/student/s",method = RequestMethod.GET)
    public String findStudent(Model model, HttpServletRequest request, String name,
                              @Validated Student student, BindingResult bindingResult){
        if(name.length()==0&& bindingResult.hasErrors()){
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors){
                logger.error(objectError.getDefaultMessage());
            }
            //将错误信息传至页面
            model.addAttribute("allErrors",allErrors);
            //出错则重新至商品更新页面
            logger.error(bindingResult);
            return "error";
        }


        if (name!=null && name!=""){
            if (studentService.findStudentByName(name)!=null){
                //返回值不是空,说明查询成功
                logger.error("查询成功");//如果出现名字重复的情况?
            }else {
                //返回为空,说明你输入的名字不在学员里
                logger.error("您输入的姓名不是我们的学员!");
                return "error";
            }
        }else {
            logger.error("你输入的名字为空");
            return "error";
        }
        List<Student> students= Collections.singletonList(studentService.findStudentByName(name));
        model.addAttribute("student",students);
        return "findStudent";
    }
    /**查询学生所有数据
     * */
    @RequestMapping(value = "/student/list",method = RequestMethod.GET)
    public String findAllStudent(Model model,
         @RequestParam(defaultValue = "1",required =true,value = "pageNo") Integer pageNo){
        ///引入分页查询，使用PageHelper分页功能,在查询之前传入当前页，然后显示多少记录
//        Integer pageSize=5;//每页显示记录数为5,设置了没有用

        PageHelper.startPage(pageNo,5);//获取第一页，5条 内容
        List<Student> studentList=studentService.findAll();
        //获取所有用户信息
        PageInfo<Student> pageInfo=new PageInfo<Student>(studentList);
        logger.error(pageInfo);
        //将获取的对象结果进行封装 只需要将pageInfo交给页面就可以
        model.addAttribute("pageInfo",pageInfo);
        return "findAllStudent";
    }

/**
 （1）请求处理类必须在 IOC 容器中
 （2）@RequestMapping 用来映射请求，其中 value 属性指定映射的 url。可以作用类上，相当于 namespace 的作用。
 （3）返回值最终会被解析为 ModelAndView 对象。结合视图解析器，返回到视图。
 */

}