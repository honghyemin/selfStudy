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
<title>글내용</title>
<script type="text/javascript">
	function backToList(obj) {
		obj.action = "${contextPath}/mct/boardMain.do";
		obj.submit();
	}
	// 수정하기 클릭 시 텍스트박스 활성화
	function fn_enable(obj) {
		document.getElementById("i_title").disabled = false;
		document.getElementById("i_content").disabled = false;
		document.getElementById("i_imageFileName").disabled = false;
		document.getElementById("tr_btn_modify").style.disabled = "block";
		document.getElementById("tr_btn").style.display = "none";

	}
	
	// 수정반영하기 클릭 시 컨트롤러에 수정 데이터 전송
	function fn_modify_article(obj) {
		obj.action = "${contextPath}/mct/modPosting.do";
		obj.submit();
	}
	
	function fn_remove_article(url, articleNum){
		var form = document.createElement("form");
		form.setAttribute("method", "post");
		form.setAttribute("action", url);
		var articleNumInput = document.createElement("input");
		articleNumInput.setAttribute("type", "hidden");
		articleNumInput.setAttribute("name", "articleNum");
		articleNumInput.setAttribute("value", articleNum);
		form.appendChild(articleNumInput);
		document.body.appendChild(form);
		form.submit();
		
	}
	
	function fn_reply_form(url, parentNum){
		var form = document.createElement("form");
		form.setAttribute("method","post");
		form.setAttribute("action", url);
		var parentNumInput = document.createElement("input");
		parentNumInput.setAttribute("type", "hidden");
		parentNumInput.setAttribute("name", "parentNum");
		parentNumInput.setAttribute("value", parentNum);
		form.appendChild(parentNumInput);
		document.body.appendChild(form);
		form.submit();
		
	}
	
	function readURL(input) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
	 }  
</script>
</head>
<body>
	<form name="frmPosting" method="post" enctype="multipart/form-data">
		<table border="0" align="center">
		<tr>
			<td width="150" align ="center" bgcolor="#FF9933">
			글번호
			</td>
			<td>
				<input type ="text" value="${board.articleNum }" disabled />
				<input type ="hidden" name="articleNum" value="${board.articleNum }" />
			</td>
		</tr>
		<tr>
			<td width="150" align="center" bgcolor="#FF9933">
			작성자 아이디
			</td>
			<td>
				<input type="text" value"${board.id }" name="writer" disabled/>
			</td>
		</tr>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">글제목</td>
			<td><input type="text" value="${board.title }" name="title"
				id="i_title" disabled /></td>
		</tr>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">글내용</td>
			<td><textarea rows="20" cols="60" name="content" id="i_content"
					disabled />${board.content }</textarea></td>
		</tr>
		<c:if
			test="${not empty board.imageFileName && board.imageFileName!='null' }">
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933" rowspan="2">이미지</td>
				<td>
					<input type="hidden" name="originalFileName"
					value="${board.imageFileName }&articleNum=${board.articleNum}"
					id="preview" /><br>
				</td>
			</tr>
			<tr>
			<tr>
				<td><input type="file" name="imageFileName "
					id="i_imageFileName" disabled onchange="readURL(this);" /></td>
			</tr>
		</c:if>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">등록일자</td>
			<td><input type=text
				value="<fmt:formatDate value="${board.writeDate}" />" disabled />
			</td>
		</tr>
		<tr id="tr_btn_modify">
			<td colspan="2" align="center">
				<input type=button value="수정반영하기" onClick="fn_modify_article(frmPosting)"> 
				<input type=button value="취소" onClick="backToList(frmPosting)"></td>
		</tr>

		<tr id="tr_btn">
			<td colspan=2 align="center">
				<%-- <c:if test="${member.id == article.id }">
	    <input type=button value="수정하기" onClick="fn_enable(this.form)">
	    <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})">
	  </c:if> --%> <input type=button value="수정하기" onClick="fn_enable(this.form)"> 
				<input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/mct/removePosting.do', ${board.articleNum})">
				<input type=button value="리스트로 돌아가기" onClick="backToList(this.form)">
				<input type=button value="답글쓰기"
				onClick="fn_reply_form('${contextPath}/mct/replyForm.do', ${board.articleNum})">
			</td>
		</tr>
		</table>
	</form>
</body>
</html>
















