<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <img src="${pageContext.request.contextPath}/static/image/picture.jpg" alt="">

                <a style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"   class="message-color" href="${pageContext.request.contextPath}/user/login/0">登录</a>

                <a style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"   class="message-color"  href="${pageContext.request.contextPath}/user/register/0">注册</a>

                <a  style="margin: 10px;white-space: nowrap;display: inline-block;font-size: 15px"  class="message-color"  href="${pageContext.request.contextPath}/user/loginOut">退出</a>
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

    <main class="main1">
        <!-- 轮播图 -->
        <div class="photo">
            <ul>
                <li><a href=""><img src="${pageContext.request.contextPath}/static/image/img1.png"></a> </li>
                <li><a href=""><img src="${pageContext.request.contextPath}/static/image/img2.png"></a></li>
                <li><a href=""><img src="${pageContext.request.contextPath}/static/image/img3.png" ></a></li>
                <li><a href=""><img src="${pageContext.request.contextPath}/static/image/img4.jpeg"></a></li>
                <li><a href=""><img src="${pageContext.request.contextPath}/static/image/img5.jpg"></a></li>
            </ul>
            <div class="ssspot"></div>
        </div>
        <!-- 大图标 -->
        <div class="main1-icon pd15">
            <div class="icon1">
                <div class="icon1-img"><img src="${pageContext.request.contextPath}/static/image/task0801main02.png" alt=""></div>
                <h4>高效</h4>
                <div>而且比看视频更高效</div>
            </div>
            <div class="icon2">
                <div class="icon2-img"><img src="${pageContext.request.contextPath}/static/image/task0801main03.png" alt="">
                </div>
                <h4>规范</h4>
                <div>标准的实战教程，不会走弯路</div>
            </div>
            <div class="icon3">
                <div class="icon3-img"><img src="${pageContext.request.contextPath}/static/image/task0801main04.png" alt="">
                </div>
                <h4>人脉</h4>
                <div>同班好友，混入职脉圈，为以后铺平道路</div>
            </div>
            <div class="icon4">
                <div class="icon4-img"><img class="sprite" src="${pageContext.request.contextPath}/static/image/task0801main05.png" alt=""> 12300</div>
                <div class="icon4-text">累计在线学习人数</div>
                <div class="icon4-img"><img class="sprite" src="${pageContext.request.contextPath}/static/image/task0801main05.png" alt=""> 541</div>
                <div class="icon4-text">学员已找到满意工作</div>
            </div>
        </div>
        <!-- 流程 -->
        <div class="main1-process pd15">
            <div class="process">
                <div class="circle">1</div>
                <div class="text"> 匹配你现在的个人情况寻找自己合适的岗位</div><img src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>

            <div class="process">
                <div class="circle">2</div>
                <div class="text">了解职业前景薪金待遇、竞争压力等</div><img src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
            <div class="process">
                <div class="circle">3</div>
                <div class="text">掌握行业内顶级技能</div><img src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
            <div class="process">
                <div class="circle">4</div>
                <div class="text">查看职业目标任务</div><img class="img1" src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
            <div class="process">
                <div class="circle">5</div>
                <div class="text">参考学习资源，掌握技能点，逐个完成任务</div><img src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
            <div class="process">
                <div class="circle">6</div>
                <div class="text">加入班级，和小伙伴们互帮互助，一块学习</div><img src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
            <div class="process">
                <div class="circle">7</div>
                <div class="text">选择导师，一路引导，加速自己成长</div><img src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
            <div class="process">
                <div class="circle">8</div>
                <div class="text">完成职业技能，升级业界大牛</div><img class="img1" src="${pageContext.request.contextPath}/static/image/task0801main06.png" alt="">
            </div>
        </div>
        <!-- 学员展示 -->
        <div class="main1-show pd15">
            <div class="show-title">优秀学员展示</div>
            <div class="show-content">
                <div class="show-frame">
                    <div class="show-img"><img src="${pageContext.request.contextPath}/static/image/task0801main07.png" alt=""></div>
                    <div class="show-title"> siri </div>
                    <div class="show-text">Apple技术总监：互联网基础服务领域，从事虚拟主机，云服务器、域名等工作。层任新网高级技术经理，负责技术研发，团队管理与建设。</div>
                </div>
                <div class="show-frame">
                    <div class="show-img"><img src="${pageContext.request.contextPath}/static/image/task0801main08.png" alt=""></div>
                    <div class="show-title">小爱</div>
                    <div class="show-text">花果山技术总监：互联网基础服务领域，从事虚拟主机，云服务器、域名等工作。层任新网高级技术经理，负责技术研发，团队管理与建设。</div>
                </div>
                <div class="show-frame">
                    <div class="show-img"><img src="${pageContext.request.contextPath}/static/image/task0801main09.png" alt=""></div>
                    <div class="show-title">小娜
                    </div>
                    <div class="show-text">巨硬技术总监：互联网基础服务领域，从事虚拟主机，云服务器、域名等工作。层任新网高级技术经理，负责技术研发，团队管理与建设。</div>
                </div>
                <div class="show-frame">
                    <div class="show-img"><img src="${pageContext.request.contextPath}/static/image/task0801main10.png" alt=""></div>
                    <div class="show-title"> 小度
                    </div>
                    <div class="show-text">百度技术总监：互联网基础服务领域，从事虚拟主机，云服务器、域名等工作。层任新网高级技术经理，负责技术研发，团队管理与建设。</div>
                </div>
                <div class="show-spot">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
            </div>
        </div>
        <!-- 合作伙伴 -->
        <div class="main1-partner pd15">
            <div class="show-title">战略合作企业</div>
            <div class="partner-content">
                <div class="main5-21"><img src="${pageContext.request.contextPath}/static/image/task0801main11.jpg" alt=""></div>
                <div class="main5-22"><img src="${pageContext.request.contextPath}/static/image/task0801main12.jpg" alt=""></div>
                <div class="main5-23"><img src="${pageContext.request.contextPath}/static/image/task0801main13.jpg" alt=""></div>
                <div class="main5-24"><img src="${pageContext.request.contextPath}/static/image/task0801main14.jpg" alt=""></div>
                <div class="main5-25"><img src="${pageContext.request.contextPath}/static/image/task0801main15.jpg" alt=""></div>
            </div>
        </div>
        <!-- 友情链接 -->
        <div class="main1-link">
            <div class="show-title">友情链接</div>
            <div class="link-list list1">
                <li>手机软件</li>
                <li>教师招聘</li>
                <li>教师招聘</li>
                <li>找&ensp;工&ensp;作</li>
                <li>教师招聘</li>
                <li>手机软件</li>
                <li>找&ensp;工&ensp;作</li>
                <li>教师招聘</li>
                <li>手机软件</li>
                <li>打&emsp;&emsp;牛</li>
            </div>
            <div class="link-list list2">
                <li>手机软件</li>
                <li>教师招聘</li>
                <li>教师招聘</li>
                <li>找&ensp;工&ensp;作</li>
                <li>教师招聘</li>
                <li>手机软件</li>
                <li>找&ensp;工&ensp;作</li>
                <li>教师招聘</li>
                <li>手机软件</li>
                <li>打&emsp;&emsp;牛</li>
            </div>
        </div>
    </main>



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
            Copyright&reg;2015技能树 &nbsp; www.jnshu.com &nbsp;&nbsp;All &nbspRights&nbsp;Reserved丨京ICP备13005880号
        </div>
    </footer>
</body>

</html>