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
<link rel="stylesheet" href="<%=cp%>/resource/css/member.css" type="text/css">
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
<div class="navigation">
	<div class="nav-bar">HOME > 회원정보목록</div>
</div>

<div class="member">
<h3>회원정보 리스트</h3>
<form name="searchForm" action="<%=cp%>/member/list.do" method="post">
	<div style="text-align: right;">
		<select name="condition" style="height: 23px;">
				<option value="userId" ${condition=="userId"?"selected='selected'":"" }>회원아이디</option>
				<option value="userName" ${condition=="userName"?"selected='selected'":"" }>회원이름</option>
		</select>
		<input type="text" name="keyword" style="vertical-align: bottom; height: 19px;">
			<button style="vertical-align: bottom; height: 23px; width: 90px; background: #eee; border: 1px solid #777;" onclick="searchList()">검색</button>
			<button type = "button" style="vertical-align: bottom; height: 23px; width: 90px; background: #eee; border: 1px solid #777;" onclick="javascript:location.href='<%=cp%>/member/list.do';">새로고침</button>
			<br>
	</div>
</form>
	
	<table class="member-table">
	<br><br>
		<tr style="border-bottom: 2px solid black;">
			<td>회원아이디</td>
			<td>회원이름</td>
			<td>전화번호</td>
			<td>이메일</td>
			<td>생년월일</td>		
		</tr>
	<c:forEach var="dto" items="${list}">
		<tr>
			<td>${dto.userId}</td>
			<td>${dto.userName}</td>
			<td>${dto.userTel}</td>
			<td>${dto.userEmail}</td>
			<td>${dto.userBirth}</td>
		</tr>
	</c:forEach>	
	</table>
	
	<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		<tr height="35">
			<td align="center">
			        ${dataCount==0?"등록된 회원이 없습니다.":paging}
			</td>
	   </tr>
	</table>
	<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
		<tr height="35">
			<td></td>
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