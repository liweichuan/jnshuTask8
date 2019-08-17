package com.jnshu.dao;

import com.jnshu.pojo.User;

import java.util.List;

public interface UserDao {
    //注册的时候需要输入验证码，把这个码使用到阿里的短信接口，校对，这步放在业务层实现
    //在Dao层就写为新增
    Long addUser(User user);
    //修改信息
    boolean updateUser(User user);
    //删除个人账号（注销）,这里需要登录之后，验证验证码才能注销，但还是根据ID删除
    boolean deleteUser(Long id);
    //登录 就是一个比对用户信息的过程，比对成功就登录成功，比对失败就是登录失败
    User findUserByName(String username);
    //展示个人信息
    User findById(Long id);
    //查找所有
    List<User> findAll();
    //这个方法是进行登录的时候模糊查询，可以通过用户名，手机号和邮箱来查找
    User findUserByLoginName(String loginname);
    //根据手机查询，判断注册时手机号合不合法，有没有被注册过
    User findUserByPhone(String phone);

}
