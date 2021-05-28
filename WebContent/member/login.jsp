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
<style>
	.mainView { width:100%; height:100%; position:relative;}
    .formView {width:1000px; height: 800px; position:relative
    ; margin-left:200px; margin-top: 200px; text-align: center;}
    .btn {margin-top: 50px; margin-left: -50px; text-align: center;}
    .writing {margin: auto;}
    .btn1 { width: 50%; height:50px;}
    .sing_search a {text-decoration: none; color:black; margin-left: -30px;;}
    .sing_search a:hover {color:red;}
    .signup {margin-right: 50px;}
</style>
</head>
<body>
	<div class="mainView">
        <div class="formView">
            <div class="writing">
                <form style="width:800px;" action = "${contextPath }/mct/login.do" method="post" enctype="utf-8">
                <h3>ID : <input style="margin-left: 80px;" type = "text" name = "id" ><br></h3>
                <h3>PASSWORD : <input type = "password" name = "pwd"><br></h3>
            </div>
            <div class="btn">
                <input class="btn1" style="width:20%; height:50px;" type="submit" value="로그인">
                
            </div>
            
        </form>
        <br><br>
        <div class="sing_search">
            <a class="signup" href="${contextPath }/member/joinForm.jsp">회원가입</a>
            <a class="searchInfo" href="${contextPath }/member/searchInfo.jsp">아이디/비밀번호 찾기</a>
        </div>
    </div>
	</div>
	

</body>
</html>