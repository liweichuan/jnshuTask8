package com.jnshu.serviceimpl;
import com.jnshu.dao.UserDao;
import com.jnshu.pojo.Result;
import com.jnshu.pojo.User;
import com.jnshu.service.UserService;
import com.jnshu.util.IntegerCastUtil;
import com.jnshu.util.Md5Util;
import com.jnshu.util.MessageUtil;
import com.jnshu.util.RedisUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger logger= LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    UserDao userDao;

    @Autowired
    Result result;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MessageUtil messageUtil;

    @Autowired
    IntegerCastUtil integerCastUtil;


    //这是返回的result的message
    String message;
    //注册，注册会生成一个token
    @Override
    public Long register(User user) {
        if (user.getType()==0){
            //手机验证码
            //从redis里面取出来验证码，key是手机号
            logger.error(redisUtil.get(user.getPhone()));//这里判断有没有取出缓存
            this.message= (String) redisUtil.get(user.getPhone());
            logger.error(message);
        }else if(user.getType()==1){
            //邮箱验证码
            //从redis里面取出来验证码，key是邮箱号
            logger.error(redisUtil.get(user.getEmail()));//这里判断有没有取出缓存
            this.message= (String) redisUtil.get(user.getEmail());
            logger.error(message);
        }
        //先确定用户名不为空，不是已经存在
        if (user.getUsername()!=null&&user.getUsername()!=""&&userDao.findUserByName(user.getUsername())==null){
            if (user.getPassword()!=null&&user.getPassword()!=""){
                if (String.valueOf(user.getMessage()).length()==6){
                    //验证码格式正确的情况下就是检查对不对
                    //发送注册码的时候，会把之前填写的信息，用户名和密码一起返回
                    if (user.getMessage().equals(this.message)){
                    //是一样的情况下，就会注册成功
                    //对用户的密码进行md5加盐之后储存在数据库中
                        String passWord = Md5Util.generate(user.getPassword());//对密码进行加密加盐
                        user.setPassword(passWord);//将加密加盐的密码存储在user的属性中
                        //将注册的用户信息存储在数据库
                        userDao.addUser(user);
                        logger.error("注册成功");
                        logger.error(user);
                        return Long.valueOf(4);
                    }else {
                        //不一样，说明验证码错误
                        logger.error(user.getMessage());
                        logger.error(this.message);
                        logger.error(this.message.equals(user.getMessage()));
                        logger.error("验证码错误");
                        return Long.valueOf(3);
                    }
                }else {
                    //验证码长度不为6
                    logger.error("验证码格式不对");
                    return Long.valueOf(2);
                }
            }else {
                //进入这层说明你的用户名输入没有问题，但是密码为空
                return Long.valueOf(1);
            }
        }else{
            //进入else说明你的用户名注册时输入为空,或已经存在
            return Long.valueOf(0);
        }
    }

    //登录，可以实现用户名，手机号，邮箱登录
    @Override
    public Result checkLogin(String loginName, String password) {
        logger.error(loginName);
        logger.error(password);
        User user = userDao.findUserByLoginName(loginName);
        logger.error(user);
        //先把user对象根据findUserByLoginName找一下，看有没有，这里登录名可以是用户名，手机号，邮箱，这里入参不好写
        //有两种思路，1.可以把这个查询分为三个查询，只要有一个查询的返回值不为null就可以进行登录的验证
        //2.可以把自己的手机字段修改为字符型，我使用第二种
        //进行密码验证，根据查询出来的user对象，我们对比他的加密的密码，1、查询的对象为空，2、查询的对象存在，但是密码不对，3、查询的用户存在，密码也验证通过
        /*这里对返回信息进行一个改变，我们使用以一个result返回，状态码，信息，和user对象
         * */
        if (user==null){
            result.setCode("401");
            result.setMessage("用户名错误");
            result.setData(null);
            return result;
        }else if (!Md5Util.verify(password,user.getPassword())){
            result.setCode("401");
            result.setMessage("密码错误");
            result.setData(null);
            return result;
        }else if (Md5Util.verify(password,user.getPassword())){
            result.setCode("200");
            result.setMessage("正确");
            result.setData(user);
            return result;
        }
//        //进行密码验证
//        if(user!=null && Md5Util.verify(passWord,user.getPassWord())){
//            return user;   //别人解释，运行到return user就停止了，经过测试验证也是这样
//        }
        result.setCode("400");
        result.setMessage("未知错误");
        result.setData(null);
        return result;
    }

    //注销
    @Override
    public boolean logout(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    //修改密码
    @Override
    public boolean changePassWord(User user) {
        return userDao.updateUser(user);
    }

    //查找个人信息
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    //根据手机号查询，看手机号有没有被注册，是否合法
    @Override
    public User findUserByPhone(String phone) {

        return userDao.findUserByPhone(phone);
    }
}
