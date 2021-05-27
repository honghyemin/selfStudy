<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*, member.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="id" value="${id }" />
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<%-- <link href="/cssfile/mypage.css" rel="stylesheet" type="text/css">--%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	showMyBoard();
	showMyInfo();
	
	
});

function showMyInfo(url){
    $(".myInfoText").click(function(){
        
        if($(".myPosting").show()){
            $(".myPosting").css("display","none");
            if($(".mypageMain").hide()){
            	$(".mypageMain").show();
            }
            	
        } 
    });
}



function showMyBoard(){
    $(".writing").click(function(){
    	
    	if($(".myPosting").css("display","none")){
       		$(".myPosting").css("display","block");
        	if($(".mypageMain").show()){
            	$(".mypageMain").css("display","none");
        	}
        } 
    });
    
    $(".writing").action="${contextPath}/mct/myPostingList.do";
}
function modInfo(){

    if($(".formInfo").css("display","none")){
        $(".formInfo").show();
    } 

}
</script>
<style>
	div {border: 3px solid black; height:800px;}
    .main > div {float: left;}
    .menubar {width :20%;  height : 100%; }
    .mainboard {  width :75%; height : 100%; margin-left: 15px;}
    .mypageText {text-align: center; font-size: 20pt; font-weight: bold;}
    .menubarList {border-top: 1px solid gray; padding-top: 20px; }
    .menubarList .myInfoText, .menubarList .writing {margin-bottom: 20px;}
    .mypageMain {width:90%; height: 60%; position: relative; margin: auto; display:block;}
    .myInfo {border:1px solid gray; height:20%;}
    .myInfoView {height : 70%; border:1px solid gray; margin-top: 10px;}
    .myInfoView > div {float: left;}
    .myInfoView > p {float: left;}
    .myPhoto {width:180px; height:200px; overflow:hidden; display:flex; align-items:center; justify-content: center;}
    .myInfoView > div> img { width:200px; margin-top: 50px; margin-left: 30px; background-size:contain;}
    #btns {position:absolute; bottom:20px; margin-left: 400px;}
    .onlyTextInfo { height: 80%; width: 60%; margin-left: 20px; position:relative;}
    .formInfo{height: 100%; width: 100%; position:absolute; margin-left: -4px; 
    			margin-top: -130px;  background-color: gray; display: none;  }

    .myPosting { width: 90%; height : 40%; margin: auto; display:none}
    .boardTitle {height:20%;  }

    .postingList {height : 75%;}
    .postingTable {width: 90%; border: 1px solid gray; margin:auto; text-align: center;}
    .postingTable th, .postingTable td { border : 1px solid gray;}



</style>
</head>
<body>
	<div class="main">
        <div class="menubar">
            <p class="mypageText">마이페이지</p>
            <div class="menubarList">
 			<input type="button" value="내정보" class="myInfoText" onclick="showMyInfo();"><br>
			<input type="button" value="내가 쓴 글" class="writing" onclick="showMyBoard();">
          	</div>


        </div>
        <div class="mainboard">
            <div class="mypageMain">
                <div class="myInfo">
                    <p style="text-align: center;">내정보</p>
                </div>
                <div class="myInfoView">
                    <div style="width: 30%; height: 80%;" class="myPhoto">
                        <img src="${pageContext.request.contextPath}\image\duke2.png" />
                    </div>
                    <div class="onlyTextInfo" >
                        <p style="margin-left: 30px;">아이디 :  &nbsp;&nbsp;&nbsp;&nbsp; ${id }</p>
                        <p style="margin-left: 30px;">이름&nbsp;&nbsp;&nbsp;&nbsp;:  &nbsp;&nbsp;&nbsp;&nbsp; ${name }</p>
                        <p style="margin-left: 30px;">이메일 :  &nbsp;&nbsp;&nbsp;&nbsp; ${email }</p>
                        <div class="formInfo">
                            <form>
                                아이디 : <input type="text" name="id" value="saveID.myId"><br>
                                비밀번호 : <input type="text" name="password" ><br>
                                이름 : <input type="text" name="name" value="name"><br>
                                이메일 : <input type="text" name="email" value="email"><br>
                                <input type="submit" value="수정완료">
                                <input type="button" value="취소" onclick="돌아가기함수">
                            </form>

                        </div>
                    </div>
                    <form id="btns" method="post">
                        <input type="button" value="내정보 수정" onclick="modInfo();">&nbsp;&nbsp;&nbsp;
                        <input type="button" value="메인으로" onclick="location.href=''">
                    </form>
                </div>
                
            </div>
            <div class="myPosting">
                    <div class="boardTitle">
                        <h3 style="text-align: center;">작성글 목록</h3>
                    </div>
                    <div class="postingList">
                        <table class="postingTable">
                            <thead>
                                <tr>
                                    <th style="width:20%;">글번호</th>
                                    <th style="width:60%;">글제목</th>
                                    <th>작성일</th>
                                </tr>
                            </thead>
                            <!--리스트가 없을 때-->
                            <tbody>
                                <tr>
                                    <td colspan="3">작성글이 없어요 ㅠ</td>
                                </tr>
                            </tbody>
                            <!--리스트가 있을 때-->
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>하이</td>
                                    <td>2021-05-27</td>
                                </tr>
                                

                            </tbody>

                        </table>

                    </div>
                </div>

        </div>
    </div>
    
	
	

</body>
</html>