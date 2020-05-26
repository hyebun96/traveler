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
<link rel="stylesheet" href="resource/css/travel_seoul.css" type="text/css">
<link rel="stylesheet" href="resource/css/style.css" type="text/css">
</head>
<body>

	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</div>
	
	<div class="navigation">
		<div class="nav-bar">HOME > TRAVEL</div>
	</div>

	<!-- 여행지   -->
	
	<div class=main>
	<c:forEach var = "dto" items="${list}">
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
				<img alt="" src="resource/img/user.png"> <span>${dto.userName}</span><br>		<%-- Admin--%>
				<span>${dto.created}</span>														<%-- April 21, 2020 --%>
				<div class="content">${dto.information}</div>
					
					<%--
					Create a blog post subtitle that
					summarizes your post in a few short, punchy sentences and entices
					your audience to continue reading. Welcome to your blog po...
					 --%>
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