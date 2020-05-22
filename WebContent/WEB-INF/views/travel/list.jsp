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
		<c:if test="${sessionScope.member.userId == 'admin'}">
			<p><button type="button" id="cr" onclick="javascript:location.href='<%=cp%>/travel/created.do'">글등록</button></p>
		</c:if>
		<div class="weather">
			<img alt="" src="<%=cp %>/resource/img/cloudy.png">
			<p style="margin-top: 20px; display: block;">seoul weather(℃)</p>		
			<p style="font-size: 12px; padding-top: 10px;">${date}</p>	<%--April 21, 2020 --%>
		</div>
	
		<c:forEach var= "dto" items="${list}">
			<div class="box">
				<div class="content-box">
					<c:forEach var="s" items="${dto.imageFilename}">
						<img alt="" src="<%=cp%>/uploads/travel/${s}">
					</c:forEach>
				</div>
				<div class="content-box2">
					<img alt="" src="<%=cp %>/resource/img/user.png">
					<span>${dto.userName}</span><br>		
					<span>${dto.place} | ${dto.created}</span>														
					<div class="content">${dto.information}</div>
				</div>
				<div id="up">
						<c:if test="${sessionScope.member.userId == 'admin'}" >
							<button type="button" onclick="javascript:location.href='<%=cp%>/travel/delete.do?num=${dto.num}'">글삭제</button>
							<button type="button" onclick="javascript:location.href='<%=cp%>/travel/update.do?num=${dto.num}'">글수정</button>
						</c:if>
					</div>
				<br>
				<div class="like-box">
					<hr style="clear: both;">
					<span>${dto.likeNum} Like&nbsp;&nbsp;&nbsp;Only read</span><span
						style="float: right;"><a href="javascript:location.href='<%=cp%>/travel/like.do?num=${dto.num}'">♥</a></span>
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