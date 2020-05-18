<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
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


<div class=main>	
	<div class="clock">
		<input 
			type="text" id="nowDate" readonly="readonly" style="color: black; width: 140px; height: 100%;
			font-size: 15px; font-family:fantasy; border: none; background-color: white; 
			text-align: center; display: inline-block; margin: 10px auto; float: right;"><img 
			alt="" src="./img/clock.png" style="width:30px; height: 30px; float: right;">
	</div>
	
	<hr style="clear: both">
  
	<img src="img/mainimg2.jpg" style="height: 550px;">	
		<br><br><br><br>
	<div class=main-title>				
		<h3>나에게로 떠나는 여행에 오신걸 환영합니다.</h3>
	</div>

	<p>일상 탈출을 위한 여행</p>
	<p>지금 바로 떠나는 국내여행</p>	
	<br>
	<div class="main-box1">
		<a href="notice.html">
		공지사항
		</a>
	</div>
	<div class="main-box2" >
		<a href="galleryphoto.html">
		갤러리
		</a>
	</div>
</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>