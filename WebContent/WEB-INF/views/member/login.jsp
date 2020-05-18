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
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/login.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<!-- login -->
<div class="main">
	<div class="login">
		<h1>Login</h1>
		<br>
	</div>
	
	<div class="index">
	 <form name="myForm" action="javascript:send();" method="post">
		<input type="text" required="required" maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="UserID">
		<span data-placeholder="UserID"></span>	

         <input type="password" required="required"  maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="Password">
         <span data-placeholder="Password"></span>
      <button type="submit">Login</button>
      </form>
    </div> 
        
    <div class="bottom-text">
        Don't have account? <a href="#">Sign up</a>
    </div>
</div>

</body>
</html>