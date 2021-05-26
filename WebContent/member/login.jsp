<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*"
	pageEncoding="UTF-8"
	isELIgnored="false" 
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />  
<%
request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인창</title>
</head>
<body>
	<form action = "${contextPath }/mct/login.do" method="post" enctype="utf-8">
	ID : <input type = "text" name = "id" ><br>
	PASSWORD : <input type = "password" name = "pwd"><br>
	<input type="submit" value="로그인">
	<input type="reset" value="다시입력">
	</form>
	<br><br>
	<a href="${contextPath }/member/joinForm.jsp">회원가입</a>
	<a href="${contextPath }/member/searchInfo.jsp">아이디/비밀번호 찾기</a>
	

</body>
</html>