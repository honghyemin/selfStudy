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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		showMyBoard();
		showMyInfo();

	});
	function showMyInfo(){
        $(".myInfoText").click(function(){
        	$(".mypageMain").css("display","block");
            if($(".myPosting").show()){
                $(".myPosting").css("display","none");
            }
            location.href="${contextPath}/mct/showMyInfo.do"
        });
    }
    function showMyBoard(){
        $(".writing").click(function(){
        	$(".myPosting").css("display","block");
            if($(".mypageMain").show()){
                $(".mypageMain").css("display","none");
            }
            location.href="${contextPath}/mct/myPostingList.do"
        });
        
    }

	function modInfo() {

		if ($(".formInfo").css("display", "none")) {
			$(".formInfo").show();
		}

	}
</script>
<style>
div {
	border: 3px solid black;
	height: 800px;
}

.main>div {
	float: left;
}

.menubar {
	width: 20%;
	height: 100%;
}

.mainboard {
	width: 75%;
	height: 100%;
	margin-left: 15px;
}

.mypageText {
	text-align: center;
	font-size: 20pt;
	font-weight: bold;
}

.menubarList {
	border-top: 1px solid gray;
	padding-top: 20px;
}

.menubarList .myInfoText, .menubarList .writing {
	margin-bottom: 20px;
}

.mypageMain {
	width: 90%;
	height: 60%;
	position: relative;
	margin: auto;
	display: block;
}


#btns {
	position: absolute;
	bottom: 20px;
	margin-left: 400px;
}

.onlyTextInfo {
	height: 80%;
	width: 60%;
	margin-left: 20px;
	position: relative;
}

.formInfo {
	height: 100%;
	width: 100%;
	position: absolute;
	margin-left: -4px;
	margin-top: -130px;
	background-color: gray;
	display: none;
}

.myPosting {
	width: 90%;
	height: 80%;
	margin: auto;

}

.boardTitle {
	height: 20%;
}

.postingList {
	height: 75%;
}

.postingTable {
	width: 90%;
	border: 1px solid gray;
	margin: auto;
	text-align: center;
}

.postingTable th, .postingTable td {
	border: 1px solid gray;
}
</style>
</head>
<body>
	<div class="main">
		<div class="menubar">
			<p class="mypageText">마이페이지</p>
			<div class="menubarList">
				<input type="button" value="내정보" class="myInfoText" ><br> 
				<input type="button" value="내가 쓴 글" class="writing" >
			</div>


		</div>
		<div class="mainboard">
			<div class="myPosting">
				<div class="boardTitle">
					<h3 style="text-align: center;">작성글 목록</h3>
				</div>
				<div class="postingList">
					<table class="postingTable">
						<thead>
							<tr>
								<th style="width: 20%;">글번호</th>
								<th style="width: 60%;">글제목</th>
								<th>작성일</th>
							</tr>
						</thead>
						<c:choose>
							<c:when test="${empty myList }">
								<tr height="10">
									<td colspan="3">
										<p align="center">
											<b><span style="font-size: 10pt;">작성글이 없어요 ㅠ</span></b>
										</p>
									</td>
								</tr>
							</c:when>

							<c:when test="${!empty myList }">
								<c:forEach var="ml" items="${myList }">
									<tr align="center">
										<td>1</td>
										<td>${ml.title }</td>
										<td>${ml.writeDate }</td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
					</table>

				</div>
			</div>

		</div>
	</div>




</body>
</html>