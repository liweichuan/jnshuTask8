<%@ page contentType="text/html;charset=GBK" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
 <head>
    <title>JSTL: -- forEach标签实例</title>
 </head>
 <body>
<h4><c:out value="forEach实例"/></h4>
 <hr>
     <%
       List a=new ArrayList();
      a.add("贝贝");
      a.add("晶晶");
      a.add("欢欢");
      a.add("莹莹");
      a.add("妮妮");
  request.setAttribute("a",a);
%>
     <B><c:out value="不指定begin和end的迭代：" /></B><br>
    <c:forEach var="fuwa" items="${a}">
     &nbsp;<c:out value="${fuwa}"/><br>
       </c:forEach>
     <B><c:out value="指定begin和end的迭代：" /></B><br>
   <c:forEach var="fuwa" items="${a}" begin="1" end="3" step="2">
    &nbsp;<c:out value="${fuwa}" /><br>
    </c:forEach>
    <B><c:out value="输出整个迭代的信息：" /></B><br>
    <c:forEach var="fuwa" items="${a}" begin="3" end="4" step="1" varStatus="s">
    &nbsp;<c:out value="${fuwa}" />的四种属性：<br>
    &nbsp;&nbsp;所在位置，即索引：<c:out value="${s.index}" /><br>
    &nbsp;&nbsp;总共已迭代的次数：<c:out value="${s.count}" /><br>
    &nbsp;&nbsp;是否为第一个位置：<c:out value="${s.first}" /><br>
    &nbsp;&nbsp;是否为最后一个位置：<c:out value="${s.last}" /><br>
 </c:forEach>
</body>
</html>
