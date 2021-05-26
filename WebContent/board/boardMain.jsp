<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import=" java.util.*, board.*"
	pageEncoding="UTF-8"
	isELIgnored="false" 
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />  
<c:set var="list" value="${postingMap.boardList }" />
<c:set var="totPosting" value="${postingMap.totPosting }" />
<c:set var="section" value="${postingMap.section }" />
<c:set var="pageNum" value="${postingMap.pageNum }" />



<%
	request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판화면</title>
<script>
	
</script>
<style>
	.no-uline{text-decoration:none;}
	.sel-page{text-decoration:none; color:red;}
	.cls1 {text-decoration:noen; text-align:center; }
	.cls2{text-align:center; margin-top: 50px;}
</style>

</head>
<body>
<table align="center" border="1"  width="80%"  >
  <tr height="10" align="center"  bgcolor="lightgreen">
     <td >글번호</td>
     <td >작성자</td>              
     <td >제목</td>
     <td >작성일</td>
  </tr>
<c:choose>
  <c:when test="${empty list }" >
    <tr  height="10">
      <td colspan="4">
         <p align="center">
            <b><span style="font-size:9pt;">등록된 글이 없습니다.</span></b>
        </p>
      </td>  
    </tr>
  </c:when>
  <c:when test="${!empty list}" >
    <c:forEach  var="posting" items="${list }" varStatus="postingNum" >
     <tr align="center">
	<td width="5%">${postingNum.count}</td>
	<td width="10%">${posting.id }</td>
	<td align='left'  width="35%">
	  <span style="padding-right:30px"></span>
	   <c:choose>
	      <c:when test='${posting.level > 1 }'>  
	         <c:forEach begin="1" end="${posting.level }" step="1">
	              <span style="padding-left:20px"></span>    
	         </c:forEach>
	         <span style="font-size:12px;">[답변]</span>
                   <a class='cls1' href="${contextPath}/mct/viewPosting.do?articleNum=${posting.articleNum}">${posting.title}</a>
	          </c:when>
	          <c:otherwise>
	            <a class='cls1' href="${contextPath}/mct/viewPosting.do?articleNum=${posting.articleNum}">${posting.title }</a>
	          </c:otherwise>
	        </c:choose>
	  </td>
	  <td width="10%"><fmt:formatDate value="${posting.writeDate}" /></td> 
	</tr>
    </c:forEach>
     </c:when>
    </c:choose>
</table>
<div class="cls2">
 <c:if test="${totPosting != null }" >
      <c:choose>
        <c:when test="${totPosting >100 }">  <!-- 글 개수가 100 초과인경우 -->
	      <c:forEach   var="page" begin="1" end="10" step="1" >
	         <c:if test="${section >1 && page==1 }">
	          <a class="no-uline" href="${contextPath }/mct/boardMain.do?section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp; 이전 </a>
	         </c:if>
	          <a class="no-uline" href="${contextPath }/mct/boardMain.do?section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
	         <c:if test="${page ==10 }">
	          <a class="no-uline" href="${contextPath }/mct/boardMain.do?section=${section+1}&pageNum=${section*10+1}">&nbsp; 다음</a>
	         </c:if>
	      </c:forEach>
        </c:when>
        <c:when test="${totPosting ==100 }" >  <!--등록된 글 개수가 100개인경우  -->
	      <c:forEach   var="page" begin="1" end="10" step="1" >
	        <a class="no-uline"  href="#">${page } </a>
	      </c:forEach>
        </c:when>
        
        <c:when test="${totPosting< 100 }" >   <!--등록된 글 개수가 100개 미만인 경우  -->
	      <c:forEach   var="page" begin="1" end="${totPosting/10 +1}" step="1" >
	         <c:choose>
	           <c:when test="${page==pageNum }">
	            <a class="sel-page"  href="${contextPath }/mct/boardMain.do?section=${section}&pageNum=${page}">${page } </a>
	          </c:when>
	          <c:otherwise>
	            <a class="no-uline"  href="${contextPath }/mct/boardMain.do?section=${section}&pageNum=${page}">${page } </a>
	          </c:otherwise>
	        </c:choose>
	      </c:forEach>
        </c:when>
      </c:choose>
    </c:if>
</div>    
<br><br>







<a  class="cls1"  href="${contextPath}/mct/writingForm.do"><p class="cls2">글쓰기</p></a>
</body>
</html>