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
<title>아이디 / 비밀번호 찾기</title>
<script type="text/javascript">
	function searchId(){
		var idf = document.myId;
		
		if(!idf.name.value){
			alert("이름을 입력하세요.");
			idf.name.focus();
			return;
		}
		
		if(!idf.email.value){
			alert("이메일을 입력하세요.");
			idf.email.focus();
			return;
		}
		
		idf.action = "${contextPath }/mct/searchId.do";
		idf.submit();
		
		
	}
	// 확인을 누르고 true가 리턴되면 보이게...
	function searchPwd1(){
		
		var pwdf = document.myPwd;
		
		if(!pwdf.pwdId.value){
			alert("아이디를 입력하세요.");
			pwdf.pwdId.focus();
			return
		}
		
		pwdf.action = "${contextPath}/mct/searchPwd1.do";
		pwdf.submit();
		
	}
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
	
	function viewPwd() {
		
		var pwdSh = document.pwdSh;

		if(pwdSh.style.display=='none'){
			pwdSh.style.display ='block';
		}
		
		
	}

</script>
<style>
	h2{text-align:center;}
	.msg {color:red; font-size:1.25em;}
	#form {border : 1px solid black;}
	#pwdSh {display :none;}
</style>
</head>
<body>
	<h2>아이디 / 비밀번호 찾기</h2>
    <hr>
    <div id="form">
	    <div id="id1">
	        <button id="btnId"> 아이디 찾기 </button>
	    </div>
	    <form name="myId" action ="${contextPath }/mct/searchId.do" enctype="utf-8" method="post">
	        이름 : <input type="text" name="name" >
	        이메일 : <input type="text" name="email"  >
	        <input type="button" value="찾기" onclick="searchId();">
	    </form>
	    <div id="pwd1">
	        <button id="btnPwd"> 비밀번호 찾기</button>
	    </div>
	    
	    <form name="myPwd" action="" enctype="utf-8" method="post">
	    <p class="pwd2">
	        아이디 : <input type="text" name="pwdId" >
	        <input type="submit" value="확인" onclick="searchPwd1();">
	    </p>
	    </form>
	    <form name="myPwd2" action="" enctype="utf-8" method="post" id="pwdSh">
		<p class="pwd3">
			이름 : <input type="text" name="name"> 
			이메일 :<input type="text" name="email"> 
			<input type="submit" value="확인" onclick="searchPwd2();">
		</p>
	</form>
	

	    <p class="msg">
	    	<b>${msg }</b>
	    </p>
    </div>
    <div>
    	<input type="button" name ="loginbtn" value="로그인하기" onclick="location.href='${contextPath}/member/login.jsp;'">
    </div>

    
    
</body>
</html>