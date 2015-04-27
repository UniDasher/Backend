<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'page.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    
 <div class="pagging">
        <div class="left">共${userNum}条记录</div>
        <c:forEach items="${users}" var="u">
          <span>${u.account}</span>  
          <span>${u.password}</span>
          <hr/>  
        </c:forEach>
         <div class="right">
        <c:if test="${currentPage == 1}">
             <span class="disabled"><< 前一页</span>
             <span class="current">1</span>        
        </c:if>
         <c:if test="${currentPage != 1}">
            <a href="getMyPage.do?page=${currentPage-1}"><< 前一页</a>
        </c:if>
        
       
           <c:if test="${currentPage == pageTimes}">
            <span>${currentPage+1 }</span>       
           </c:if>
           <c:if test="${currentPage != pageTimes}">
             <a href="getMyPage.do?page=${currentPage+1}">${currentPage+1}</a>
           </c:if>
       
         
         
        <c:if test="${currentPage == pageTimes}">
             <span class="disabled">后一页 >></span>        
         </c:if>
        <c:if test="${currentPage != pageTimes}">
        <a href="getMyPage.do?page=${currentPage+1}">后一页 >></a>
        </c:if>
         </div>
 </div> 
  </body>
</html>
