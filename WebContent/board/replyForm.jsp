<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*, board.*" pageEncoding="UTF-8" isELIgnored="false"%>
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
<title>답글작성</title>
</head>
<body>
	<h1 style="text-align:center">답글쓰기</h1>
	<form name="frmReply" method="post" action="${contextPath }/mct/addReply.do" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td align="right">글쓴이:&nbsp; </td>
				<td><input type="text" size="5" value="name" disabled /></td>
			</tr>
			<tr>
				<td align="right">글제목:&nbsp;</td>
				<td><input type="text" size="67" maxlength="100" name="title" /></td>
			</tr>
			<tr>
				<td align="right" valign="top">글내용&nbsp;</td>
				<td><textarea name="content" rows="10" coss="65" maxlength="4000" value="content" /></textarea></td>
			</tr>
			<tr>
				<td align="right">이미지파일 첨부:</td>
				<td><input type="file" name="imageFileName" onchange="readURL(this);" /></td>
				<td><img id="preview" src="#" width=200 height=200 /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
					<input type=submit value="답글 작성" />
					<input type=button value="취소" onClick="backToList(this.form)" />
				</td>
			</tr>
			
		</table>
	
	</form>


</body>
</html>