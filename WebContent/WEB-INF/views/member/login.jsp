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


<script type="text/javascript">
function bgLabel(ob, id) {
    if(!ob.value) {
	    document.getElementById(id).style.display="";
    } else {
	    document.getElementById(id).style.display="none";
    }
}

function sendLogin() {
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/login_ok.do";
    f.submit();
}

</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > Login</div>
</div>

<!-- login -->
<div class="main">
	<div class="login">
		<h1>Login</h1>
		<br>
	</div>
	
 		<p><span style="color: blue;">${message}</span>  <p> 
	<div class="index">
	 <form name="loginForm" action="javascript:send();" method="post">
		<input type="text" name="userId" required="required" maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="UserID">
		<span data-placeholder="UserID"></span>	

         <input type="password" name="userPwd" required="required"  maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="Password">
         <span data-placeholder="Password"></span>
      <button type="button" onclick="sendLogin();">Login</button>
      </form>
    </div> 
        
    <div class="bottom-text">
		<a href="<%=cp%>/">아이디찾기/</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<%=cp%>/">패스워드찾기</a>&nbsp;&nbsp;&nbsp;<br>
        Don't have account? <a href="<%=cp%>/member/member.do">Sign up</a>
    </div>
</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>