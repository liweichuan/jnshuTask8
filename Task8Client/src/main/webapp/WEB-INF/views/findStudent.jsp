<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <title>学生信息</title>
    <style type="text/css">
        h1{color: blue}
        {background-size: auto}
    </style>
</head>
<body>
<%--居中对齐--%>
<h1 style="text-align: center">学生信息</h1>

<table width="100%" border="1">
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
    <c:forEach items="${student}" var="student" >
        <tr>
            <td>${student.name}</td>
            <td>${student.qq}</td>
            <td>${student.type}</td>
            <td>${student.start_time}</td>
            <td>${student.graduation}</td>
            <td>${student.student_id}</td>
            <td>${student.link}</td>
            <td>${student.wish}</td>
            <td>${student.senior}</td>
            <td>${student.status}</td>
            <td>${student.create_by}</td>
            <td>${student.update_by}</td>
            <td>${student.create_time}</td>
            <td>${student.update_time}</td>
            <td><a href="${pageContext.request.contextPath}/student/u/${student.student_id}">编辑</a></td>
            <td><form action="${pageContext.request.contextPath}/student/${student.name}" method="post">
                <input type="hidden" name="_method" value="DELETE">
                <input TYPE="submit" value="删除"></form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>