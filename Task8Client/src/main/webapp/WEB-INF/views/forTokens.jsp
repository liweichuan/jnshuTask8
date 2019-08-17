<%--<c:forTokens> 用于浏览字符串，并根据指定的字符串截取字符串--%>
<%-- 语法：<c:forTokens items="stringOfTokens" delims="delimiters" [var="name" --%>
<%-- begin="begin" end="end" step="len" varStatus="statusName"]></c:forTokens>--%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
     <head>
        <title>JSTL: -- forTokens标签实例</title>
     </head>
  <body>
         <h4>
           <c:out value="forToken实例" />
         </h4>
      <hr>
         <c:forTokens items="北、京、欢、迎、您" delims="、" var="c1">
            <c:out value="${c1}"></c:out>
         </c:forTokens>
         <br>
         <c:forTokens items="123-4567-8854" delims="-" var="t">
            <c:out value="${t}"></c:out>
         </c:forTokens>
         <br>
         <c:forTokens items="1*2*3*4*5*6*7" delims="*" begin="1" end="3"
            var="n" varStatus="s">
         &nbsp;<c:out value="${n}" />的四种属性：<br>
         &nbsp;&nbsp;所在位置，即索引：<c:out value="${s.index}" />
             <br>
         &nbsp;&nbsp;总共已迭代的次数：<c:out value="${s.count}" />
             <br>
         &nbsp;&nbsp;是否为第一个位置：<c:out value="${s.first}" />
             <br>
         &nbsp;&nbsp;是否为最后一个位置：<c:out value="${s.last}" />
             <br>
         </c:forTokens>
     </body>
</html>