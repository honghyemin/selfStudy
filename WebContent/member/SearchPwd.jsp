<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*, member.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="id" value="${member.id }" />
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 확인하기</title>
<script>

function searchPwd2(){
	
	var pwdf2 = document.myPwd2;
	
	if(!pwdf2.name.value){
		alert("이름을 입력하세요.");
		pwdf2.name.focus();
		return
	}
	
	if(!pwdf2.email.value){
		alert("이메일을 입력하세요.");
		pwdf2.email.focus();
		return
	}
	pwdf2.action = "${contextPath}/mct/searchPwd2.do";
	pwdf2.submit();
	
}
</script>
</head>
<body>
	<form name="myPwd2" action="" enctype="utf-8" method="post">
		<p class="pwd3">
			이름 : <input type="text" name="name"> 
			이메일 :<input type="text" name="email"> 
			<input type="submit" value="확인" onclick="searchPwd2();">
		</p>
	</form>
	
	<p class="msg">
	    	<b>${msg }</b>
	    </p>

</body>
</html>