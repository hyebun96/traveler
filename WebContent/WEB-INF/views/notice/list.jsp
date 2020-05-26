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
<link rel="stylesheet" href="<%=cp%>/resource/css/board.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.submit();
	}
	
	function writeNotice() {
		<c:if test="${sessionScope.member.userId=='admin'}">
	    var page = "${page}";
	    var query = "&page="+page;
	    var url = "<%=cp%>/notice/write.do?" + query;

	    location.href=url;
	    </c:if>

	    <c:if test="${sessionScope.member.userId!='admin'}">
	    var page = "${page}";
	    var query = "&page="+page;
	    var url = "<%=cp%>/notice/access.do?" + query;
	    location.href=url;
	 	</c:if>
	   
	
	}
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > 공지사항</div>
</div>

<div class="board">
<h3>공지사항</h3>
<form name="searchForm" action="<%=cp%>/notice/notice.do" method="post">
<div style="text-align: right;">
	<select name="condition" style="height: 23px;">
			<option value="title" ${condition=="title"?"selected='selected'":"" }>글제목</option>
			<option value="contents" ${condition=="contents"?"selected='selected'":"" }>글내용</option>
	</select>
	<input type="text" name="keyword" style="vertical-align: bottom; height: 19px;">
	<button style="vertical-align: bottom; height: 23px; width: 100px; background: #eee; border: 1px solid #777;" onclick="searchList()">검색</button>
</div>
</form>
	
	<table class="board-table">
		<tr style="border-bottom: 2px solid black;">
			<td>글번호</td>
			<td style="width: 50%"><a>제목</a></td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>		
		</tr>
	<c:forEach var="dto" items="${importantList }" begin="0" end="4">
		<tr>
			<td ><span style="display: inline-block;padding:1px 3px; background: #ED4C00;color: #FFFFFF; ">중요!</span></td>
			<td class="board-title"><a href="${articleUrl }&num=${dto.num}">${dto.title }</a></td>
			<td>${dto.name }</td>
			<td>${dto.created }</td>
			<td>${dto.viewCount }</td>
		</tr>
	</c:forEach>
	<c:forEach var="dto" items="${list }">
		<tr>
			<td>${dto.listNum }</td>
			<td class="board-title"><a href="${articleUrl }&num=${dto.num}">${dto.title }</a></td>
			<td>${dto.name }</td>
			<td>${dto.created }</td>
			<td>${dto.viewCount }</td>
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
	
	
	<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		<tr height="35">
			<td><button type = "button" onclick="javascript:location.href='<%=cp%>/notice/notice.do';">새로고침</button></td>
			<td align="right"><button type = "button" onclick="writeNotice();">글등록</button></td>
		</tr>
	</table>
	
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>


</body>
</html>