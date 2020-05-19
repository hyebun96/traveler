<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="<%=cp %>/resource/css/travel.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/resource/css/main.css" type="text/css">
</head>
<body>

	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>

	<!-- 여행지   -->
	
	<div class=main>
	
	<%--히든 메뉴 관리자 --%>
	<p><button type="button" id="cre">글등록</button></p>
	
	<c:forEach var= "dto" items="${list}">
		<div class="weather">
			<img alt="" src="resource/img/cloudy.png">
			<p style="margin-top: 20px; display: block;">seoul weather(℃)</p>		
			<p style="font-size: 12px; padding-top: 10px;">${date}</p>	<%--April 21, 2020 --%>
		</div>
		<div class="box">
			<div class="content-box">
				<img alt="" src="<%=cp%>/uploads/travel/${dto.imageFilename}">
			</div>
			<div class="content-box2">
				<img alt="" src="<%=cp %>/resource/img/user.png">
				<span>${dto.userName}</span><br>		
				<span>${dto.place} | ${dto.created}</span>														
				<div class="content">${dto.infomation}</div>
			</div>
			<br>
			<div class="like-box">
				<hr>
				<span>0 Like&nbsp;&nbsp;&nbsp;Only read</span><span
					style="float: right;"><a href="#">♥</a></span>
			</div>
		</div>
		<br>
	</c:forEach>

	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

</body>
</html>