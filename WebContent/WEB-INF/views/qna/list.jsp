<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/qna.css" type="text/css">

<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.submit();
	}
	
	function memberOnly(){
		alert("게시물 접근 권한이 없습니다.")
		return;
	}
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > Q&A</div>
</div>

<div class="qna">
<h3>Q & A</h3>
<form name="searchForm" action="<%=cp%>/qna/list.do" method="post">
<div style="text-align: right;">
	<select name="condition" style="height: 23px;">
			<option value="subject" ${condition=="subject"?"selected='selected'":"" }>글제목</option>
			<option value="content" ${condition=="content"?"selected='selected'":"" }>글내용</option>
			<option value="userName" ${condition=="userName"?"selected='selected'":"" }>작성자</option>
	</select>
	<input type="text" name="keyword" style="vertical-align: bottom; height: 19px;">
	<button style="vertical-align: bottom; height: 23px; width: 100px; background: #eee; border: 1px solid #777;" onclick="searchList()">검색</button>
</div>
</form>
	
	<table class="qna-table">
		<tr style="border-bottom: 2px solid black;">
			<td>종류</td>
			<td>글번호</td>
			<td style="width: 50%"><a>제목</a></td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>		
		</tr>
	<c:set var="qnaId" value=""/>
	<c:forEach var="dto" items="${list}">
		<tr>
			<td>${dto.depth==0 ? "질문":"답변"}</td>
			<td>${dto.listNum}</td>
			<td class="qna-title">
				<c:choose>
					<c:when test="${dto.depth==0}">
						<c:set var="qnaId" value="${dto.userId}"/>
						<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
							<a href="${viewUrl}&qnaNum=${dto.qnaNum}">${dto.subject}</a>
						</c:if>
						<c:if test="${sessionScope.member.userId!=dto.userId && sessionScope.member.userId!='admin'}"><a onclick="memberOnly();">${dto.subject}</a></c:if>
					</c:when>
					<c:otherwise>
						&nbsp;└&nbsp;re:
						<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==qnaId}">
							<a href="${viewUrl}&qnaNum=${dto.qnaNum}">${dto.subject}</a>
						</c:if>
						<c:if test="${sessionScope.member.userId!=qnaId && sessionScope.member.userId!='admin'}"><a onclick="memberOnly();">${dto.subject}</a></c:if>
					</c:otherwise>
				</c:choose>
			</td>
			<td>${dto.userName}</td>
			<td>${dto.qnaDate}</td>
			<td>${dto.hitCount}</td>
		</tr>
	</c:forEach>
			
	</table>
	
	<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		<tr height="35">
			<td align="center">
			        ${dataCount==0?"등록된 게시물이 없습니다.":paging}
			</td>
	   </tr>
	</table>
	
	
	
	<div>
		<button type = "button" onclick="javascript:location.href='<%=cp%>/qna/list.do';">새로고침</button>
		<button type = "button" onclick="javascript:location.href='<%=cp%>/qna/write.do';" style="float: right">글등록</button>
	</div>
	
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>



</body>
</html>