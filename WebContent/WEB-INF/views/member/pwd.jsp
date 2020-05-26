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
	var f = document.pwdForm
    
	str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "<%=cp%>/member/pwd_ok.do";
    
    f.submit();
}

</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > MyPage</div>
</div>

<!-- login -->
<div class="main">
	<div class="login">
		<span style="font-weight: bold; font-size:27px; color: #424951;">패스워드 재확인</span>
		   <br><br><h4>정보보호를 위해 패스워드를 다시 입력해주세요.</h4>
		<br>
	</div>
	
	<div class="index">
		<form name="pwdForm" action="javascript:send();" method="post">	 
			<input type="text" name="userId" required="required" maxlength="10" pattern="[a-zA-Z0-9]+"  value="${sessionScope.member.userId}" readonly="readonly">
			<span data-placeholder="UserID"></span>	
			<input type="password" name="userPwd" required="required"  maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="Password">
			<span data-placeholder="Password"></span>
			<input type="hidden" name="mode" value="${mode}">
			<button type="button" onclick="sendLogin();">확인</button>
		</form>
		
		<table style="font-size:15px; width:420px; margin-top:-210px; margin-left:50px; border-collapse: collapse;">
			<tr align="left" height="30" >
				<td><span style="color: blue;">${message}</span></td>
			</tr>
		</table>
    </div> 
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
</body>
</html>