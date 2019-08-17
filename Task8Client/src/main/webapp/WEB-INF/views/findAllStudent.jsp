<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib uri="/tags" prefix="date"%>
<%--//引入依赖，jstl标签库--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
         isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<title></title>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>task14-1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/task14.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/task14-chen.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">


    <base href="${pageContext.request.contextPath}/student/list">
    <title>学生列表</title>
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


<body style="font-size: 10px">
<%--添加用户直接访问toadd方法--%>
<h2><a href="${pageContext.request.contextPath}/student/a" >添加用户</a> </h2>
<%--${pageContext.request.contextPath}是绝对路径--%>
<form action="${pageContext.request.contextPath }/student/s" method="GET">
    <label for="name">姓名：</label>
    <input id="name" name="name" >
    <input type="submit" value="查询"/>
</form>
<table width="100%" border="1">
    <tbody>
    <tr>
        <th>姓名</th>
        <th>QQ</th>
        <th>学习类型</th>
        <th>入学时间</th>
        <th>毕业院校</th>
        <th>学号</th>
        <th>日报链接</th>
        <th>愿望</th>
        <th>师兄</th>
        <th>状态</th>
        <th>数据创建人</th>
        <th>数据更新人</th>
        <th>数据创建时间</th>
        <th>数据更新时间</th>
    </tr>
    <c:forEach items="${pageInfo.list}" var="student">
        <tr>
            <td>${student.name}</td>
            <td>${student.qq}</td>
            <td>${student.type}</td>
            <td><date:date value="${student.start_time}"/></td>
            <td>${student.graduation}</td>
            <td>${student.student_id}</td>
            <td>${student.link}</td>
            <td>${student.wish}</td>
            <td>${student.senior}</td>
            <td>${student.status}</td>
            <td>${student.create_by}</td>
            <td>${student.update_by}</td>
            <td><date:date value="${student.create_time}"/></td>
            <td><date:date value="${student.update_time}"/></td>
            <td><a href="${pageContext.request.contextPath}/student/u/${student.student_id}">编辑</a></td>
            <td><form action="${pageContext.request.contextPath}/student/${student.name}" method="post">
                <input type="hidden" name="_method" value="DELETE">
                <input TYPE="submit" value="删除"></form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<center>
    <p>当前 ${pageInfo.pageNum}页,总${pageInfo.pages}
        页,总 ${pageInfo.total}条记录</p>
    <a href="/findAllStudent?pageNo=${pageInfo.firstPage}">1</a>

    <c:if test="${pageInfo.lastPage}!=1">
        <a href="/findAllStudent?pageNo=${pageInfo.firstPage+1}">2</a>
    </c:if>
    <c:if test="${pageInfo.hasPreviousPage}">
        <a href="/findAllStudent?pageNo=${pageInfo.pageNum-1}">上一页</a>
    </c:if>
    <c:if test="${pageInfo.hasNextPage }">
        <a href="/findAllStudent?pageNo=${pageInfo.pageNum+1}">下一页</a>
    </c:if>
    <c:if test="${pageInfo.lastPage}!=1">
        <a href="/findAllStudent?pageNo=${pageInfo.lastPage}">${pageInfo.lastPage}</a>
    </c:if>
</center>
</body>

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

