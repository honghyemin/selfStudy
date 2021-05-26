<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*, member.*"
	pageEncoding="UTF-8"
	isELIgnored="false" 
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />  
<%
	request.setCharacterEncoding("UTF-8");
%> 
<%
	String id = request.getParameter("id");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결과창</title>
<script>
	alert("<%=id %>님 안녕하세요!");
	window.location.href = "${contextPath }/mct/boardMain.do"; 
	
</script>
</head>
<body>
	
	
	
	
	
</body>
</html>