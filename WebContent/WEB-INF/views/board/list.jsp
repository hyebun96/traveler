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
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="board">
<h3>자유게시판</h3>
<div style="text-align: right;">
	<select name="category" style="height: 23px;">
			<option value="">:: 선택 ::</option>
			<option value="title">글제목</option>
			<option value="contents">글내용</option>
			<option value="writer">작성자</option>
	</select>
	<input type="text" name="search" style="vertical-align: bottom; height: 19px;">
	<button style="vertical-align: bottom; height: 23px; width: 100px; background: #eee; border: 1px solid #777;">검색</button>
</div>


	<table class="board-table">
		<tr style="border-bottom: 2px solid black;">
			<td>게시번호</td>
			<td style="width: 50%">제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수s</td>		
		</tr>
		<tr>
			<td>1</td>
			<td class="board-title">오늘은...</td>
			<td>듀듀</td>
			<td>2020-04-23</td>
			<td>3</td>
		</tr>
		<tr>
			<td>2</td>
			<td class="board-title">빨리 다음주가 왔으면 좋겠다^^</td>
			<td>버즈</td>
			<td>2020-04-24</td>
			<td>100</td>
		</tr>
			
	</table>
	<div class="number">
		<a href="#">이전</a>
		<a href="#">1</a>
		<a href="#">2</a>
		<a href="#">3</a>
		<a href="#">다음</a>
	</div>
	
	<div align="right">
			<button>목록으로</button>
	</div>
	
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>


</body>
</html>