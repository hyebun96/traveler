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
<link rel="stylesheet" href="<%=cp%>/resource/css/contact.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="member-body">
		<div class="title">
			<h1>CONTACT</h1>
		</div>	
		<form name="contact" method="post">
			<ul class="list-row">
				<li class="col-sub">이름</li>
				<li class="col-input">
					<input type="text" name=contactName class="boxTF">
				</li>
			</ul>
			
			<ul class="list-row">
				<li class="col-sub">이메일</li>
				<li class="col-input">
					<input type="text" name=contactEmail class="boxTF">
				</li>
			</ul>
			
			<ul class="list-row">
				<li class="col-sub">전화번호</li>
				<li class="col-input">
					<input type="text" name=contactTel class="boxTF">
				</li>
			</ul>
			
			<ul class="list-row" style="clear: both;">
				<li class="col-sub">분류</li>
				<li class="col-input">
					  <select name="contactSort" class="selectField">
							<option value="">::선 택::</option>
							<option value="sugg">제안</option>
							<option value="edit">정보수정요청</option>
							<option value="ad">광고문의</option>
							<option value="etc">기타</option>
					  </select>				
				</li>
			</ul>
			<ul class="list-row" style="clear: both;">
				<li class="col-sub">내용</li>
			</ul>
			<ul class="list-row" style="clear: both;">
				<li style="margin-bottom: 100px;">
					<textarea id="story" name="story" rows="15" cols="54" style="resize: none;" ></textarea></li>	
				<li class="col-btn" style="clear: both; margin-top: 250px;" >
					<button type="reset" class="btn"> 다시입력 </button>
				    <button type="submit" class="btn"> 전송 </button>
				</li>
			</ul>
		</form>
	</div>
	
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>

</body>
</html>