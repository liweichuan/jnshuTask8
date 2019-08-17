<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
操作失败!<br>
错误原因:<br>
<%--error接受错误信息--%>
<c:if test="${allErrors!=null}">
    <c:forEach items="${allErrors}" var="Error">
        ${Error.defaultMessage}
    </c:forEach>
</c:if>

<json:object >
    <json:property name="code" value="${code}"/>
    <json:property name="msg" value="${msg}"/>
</json:object>

<a href="${pageContext.request.contextPath}/student/list">返回所有学生</a>
</body>
</html>
