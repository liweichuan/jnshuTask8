<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>编辑学生</title>
</head>
<body>
<h1>修改用户</h1>

<form name="studentForm" action="${pageContext.request.contextPath}/student/u" method="post">

    <input type="hidden" name="id" value="${student.id}"><br>
    姓名：<input type="text" name="name" value="${student.name}"><br>
    QQ:<input type="text" name="qq" value="${student.qq}"><br>
    学习类型：<input type="text" name="type" value="${student.type}"><br>
    入学时间：<input type="text" name="start_time" value="${student.start_time}"><br>
    毕业院校：<input type="text" name="graduation" value="${student.graduation}"><br>
    学号：<input type="text" name="student_id" value="${student.student_id}"><br>
    日报链接：<input type="text" name="link" value="${student.link}"><br>
    愿望：<input type="text" name="wish" value="${student.wish}"><br>
    师兄：<input type="text" name="senior" value="${student.senior}"><br>
    状态（0代表在学，1退学，2结业）：<input type="text" name="status" value="${student.status}"><br>
    创建人：<input type="text" name="create_by" value="${student.create_by}"><br>
    更新人：<input type="text" name="update_by" value="${student.update_by}"><br>
    创建时间：<input type="text" name="create_time" value="${student.create_time}"><br>
    更新时间：<input type="text" name="update_time" value="${student.update_time}"><br>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
