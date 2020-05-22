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
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.submit();
	}
</script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
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
	<c:forEach var="dto" items="${list}">
		<tr>
			<td>${dto.depth==0 ? "질문":"답변"}</td>
			<td>${dto.listNum}</td>
			<td class="qna-title">
				<c:if test="${dto.depth!=0}">└&nbsp;</c:if>
				<a href="${viewUrl}&qnaNum=${dto.qnaNum}">${dto.subject}</a></td>
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
	
	
	
	<div align="right">
			<button type = "button" onclick="javascript:location.href='<%=cp%>/qna/write.do';">글등록</button>
	</div>
	
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>


</body>
</html>