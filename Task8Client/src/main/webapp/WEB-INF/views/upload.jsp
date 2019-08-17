<%--
  Created by IntelliJ IDEA.
  User: liweichuan
  Date: 2019/7/29
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>commonfileupload 上传文件示例</title>
</head>
<body>
<div style="font-size: 2px">
    commonfileupload 上传文件示例 <br>
    <form method="post" action="${pageContext.request.contextPath}/u/image" ENCTYPE="multipart/form-data">
        文件:<input type="file" name="file">
        <input type="submit" value="上传" name="submit">
    </form>
</div>
</body>
</html>