<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*, member.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입창</title>
</head>
<body>
	<form method="post" action="${contextPath }/mct/joinForm.do" enctype="utf-8">
		<h1>회원이 되고싶으신가요??</h1>
		<hr>
		<table id="join">
			<tr>
				<td>아이디 : <input type="text" name="id"></td>
			</tr>
			<tr>
				<td>비밀번호 : <input type="password" name="pwd"></td>
			</tr>

			<tr>
				<td>이름 : <input type="text" name="name"></td>
			</tr>

			<tr>
				<td>이메일 : <input type="text" name="email"></td>
			</tr>
			<tr>
				<td><input type="submit" value="가입하기"><input
					type="reset" value="다시입력"></td>
			</tr>

		</table>
	</form>
</body>
</html>