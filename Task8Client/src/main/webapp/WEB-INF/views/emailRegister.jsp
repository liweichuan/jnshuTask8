<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%--//引入依赖，jstl标签库--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
         isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>task14-1</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/task14.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/task14-chen.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">

</head>

<body>
<header>
    <div class="header-top pd15">
        <div class="service">客服热线：010-594-78634</div>
        <div class="micrologo" >
            <%--                这里用来放头像  之后存在数据库        --%>
            <img src="" alt="">

            <a style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"   class="message-color" href="${pageContext.request.contextPath}/user/login/0">登录</a>

            <a style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"   class="message-color"  href="${pageContext.request.contextPath}/user/register/0">注册</a>

            <a  style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"  class="message-color"  href="${pageContext.request.contextPath}/user/loginOut">退出</a>

            <a  style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"  class="message-color"  href="${pageContext.request.contextPath}/u/user">我的</a>

            <img src="${pageContext.request.contextPath}/static/image/task08header01.png" alt="">
            <img src="${pageContext.request.contextPath}/static/image/task08header02.png" alt="">
            <img src="${pageContext.request.contextPath}/static/image/task08header03.png" alt="">
        </div>
    </div>


    <div class="header-bottom">
        <input type="checkbox" class="header-button">


        <div class="header-nav">
            <div class="guide">
                <a href="">首页</a>
                <div class="guide1"></div>
            </div>
            <div class="guide">
                <a href="">职业</a>
                <div class="guide1"></div>
            </div>
            <div class="guide">
                <a href="">推荐</a>
                <div class="guide1"></div>
            </div>
            <div class="guide">
                <a href="">关于</a>
                <div class="guide1"></div>
            </div>
        </div>
    </div>
</header>

<center>
    <div class="container-fluid" style="font-size: 10px">
        <div class="row cb-slideshow-text-container ">
            <div class="tm-content col-xl-6 col-sm-8 col-xs-8 ml-auto section">
                <header class="mb-5"><h1>注册界面</h1>
                    <a href="${pageContext.request.contextPath}/user/register/0">短信</a>
                    <a href="${pageContext.request.contextPath}/user/register/1">邮件</a>
                </header>


                <form id="form" action="${pageContext.request.contextPath}/user/register" method="post" class="subscribe-form">
                    <div class="row form-section">
                        <table>
                            <tr>
                                <td>用户名：</td>
                                <td><label>
                                    <input type="text" name="username" />
                                </label>
                                </td>
                            </tr>
                            <tr>
                                <td>密码：</td>
                                <td><label>
                                    <input type="text" name="password" />
                                </label>
                                </td>
                            </tr>
                            <tr>
                                <td>再次输入密码：</td>
                                <td><label>
                                    <input type="text" name="password2" />
                                </label>
                                </td>
                            </tr>
                            <tr>
                                <td>邮箱：</td>
                                <td><label>
                                    <input id="email" type="text" name="email" />
                                </label>
                                </td>
                            </tr>
                            <tr>
                                <td>验证码：</td>
                                <td><label>
                                    <input id="Code" type="text" name="message" />
                                </label>
                                </td>
                                <td>
                                    <div align="center">
                                        <input id="getCode" type="button" onclick="sendCode(this)" value="发送验证码"  style="color:#BC8F8F">
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="2">
                                    <br>
                                    <div align="center">
                                        <input type="submit" value="注册" style="color:#BC8F8F">
                                    </div>
                                    <div align="center">
                                        <input type="reset" value="重置" style="color:#BC8F8F">
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</center>

<script type="text/javascript">
    //点击按钮后，时间为60秒，自动倒计时
    var clock = "";
    var nums = 60;
    var btn;
    function doLoop() {
        nums--;
        if (nums > 0) {
            btn.value = "重新获取（"+nums+"）";
        } else {
            clearInterval(clock); //清除js定时器
            btn.disabled = false;
            btn.value = "点击发送验证码";
            nums = 60; //重置时间
        }
    }
    //发送邮箱验证码
    function sendCode(thisBtn) {
            console.log(thisBtn);
            var email=$('#email').val();
            $.ajax({
                //ajax发送请求信息
                // url: "/user/email",
                url: "${pageContext.request.contextPath}"+"/user/email",
                // url: "http://localhost:8080/user/email",
                //这里传数据
                data:{"email":email},
                type: "POST",
                success() {
                    alert("邮件发送成功")
                }
            });
            btn = thisBtn;
            btn.disabled = true; //将按钮置为不可点击
            btn.value = "重新获取("+nums+")";
            //计时器
            clock = setInterval(doLoop, 1000); //一秒执行一次
    }
</script>





<footer class="">
    <div class="footer-tp pd15">
        <div class="footer-tp-le">
            <div class="footer-tp-le-tp">技能树&nbsp;—&nbsp;改变你我</div>
            <div class="footer-tp-le-bt">
                <a href="http://www.jnshu.com/material/us">关于我们</a> 丨<a
                    href="http://www.jnshu.com/material/contact">联系我们</a>丨
                <a href="http://www.jnshu.com/partner">合作企业</a>
            </div>
        </div>
        <div class="footer-tp-mid">
            <div class="footer-tp-mid-tp">旗下网站</div>
            <div class="footer-tp-mid-bt">
                    <span class="">
                        <a href="http://www.caochuanyun.com/"> 草船云孵化器</a> </span>
                <span class="">
                        <a href="http://www.jnshu.com/home">最强IT特训营</a></span>

                <span class="">
                        <a href="http://game.ptteng.com/">葡萄藤轻游戏</a></span>
                <span class="">
                        <a href="http://wiki.ptteng.com/">桌游精灵</a>
                    </span>

            </div>

        </div>
        <div class="footer-tp-ri">
            <div class="footer-tp-ri-text"> 微信公众平台</div>
            <img class="footer-tp-ri-img" src="${pageContext.request.contextPath}/static/image/task08footer01.png" alt="">
        </div>
    </div>

    <div class="footer-bt">
        Copyright</div>
</footer>
</body>
</html>


