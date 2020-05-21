<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
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
<link rel="stylesheet" href="<%=cp%>/resource/css/signup.css" type="text/css">



<script type="text/javascript"> /* 자바스크립트는  웹브라우저에 의해 실행되어지는거,전세계적으로 많이 씀*/

function memberOk() {
	var f = document.memberForm;
	var str;

	str = f.userId.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요. ");
		f.userId.focus();
		return;
	}
	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) { 
		alert("아이디는 5~10자이며 첫글자는 영문자이어야 합니다.");
		f.userId.focus();
		return;
	}
	f.userId.value = str;

	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.userPwd.focus();
		return;
	}
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) { 
		alert("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;

	if(str!= f.userPwdCheck.value) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwdCheck.focus();
        return;
	}
	
    str = f.userName.value;
	str = str.trim();
    if(!str) {
        alert("이름을 입력하세요. ");
        f.userName.focus();
        return;
    }
    f.userName.value = str;

    str = f.userBirth.value;
	str = str.trim();
    if(!str || !isValidDateFormat(str)) {
        alert("생년월일를 입력하세요[YYYY-MM-DD]. ");
        f.userBirth.focus();
        return;
    }
    
    str = f.Tel1.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.Tel1.focus();
        return;
    }

    str = f.Tel2.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.Tel2.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.Tel2.focus();
        return;
    }

    str = f.tel3.value;
	str = str.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.tel3.focus();
        return;
    }
    if(!/^(\d+)$/.test(str)) {
        alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value;
	str = str.trim();
    if(!str) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

    var mode="${mode}";
    if(mode=="created") {
    	f.action = "<%=cp%>/member/member_ok.do";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/member/update_ok.do";
    }

    f.submit();
}

function changeEmail() {
    var f = document.memberForm;
	    
    var str = f.selectEmail.value;
    if(str!="direct") {
        f.Email2.value=str; 
        f.Email2.readOnly = true;
        f.Email1.focus(); 
    }
    else {
        f.Email2.value="";
        f.Email2.readOnly = false;
        f.Email1.focus();
    }
}

function userIdCheck() {
	// 아이디 중복 검사
	
}
</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>


<div class="main">
	<div class="signup">
		<h1>Sign&nbsp;up</h1>
		<br>
	</div>
	
	<div class="index">
	  <form name="memberForm" action="javascript:send();" method="post">
	  	<input type="file" name="upload" accept="image/*" class="btn" size="53" style="height: 25px;">
			<c:if test="${mode=='update'}">
				<tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
					<td width="100" bgcolor="#eeeeee" style="text-align: center;">등록이미지</td>
					<td style="padding-left:10px;"> 
						<img src="<%=cp%>/uploads/photo/${dto.imageFilename}" onclick="imageViewer('<%=cp%>/${dto.imageFilename}');">
					</td>
				</tr> 
			</c:if>			
	  	
			
		<input type="text" name="userId" value="${dto.userId}" class="imo" required="required" maxlength="15" pattern="[a-zA-Z0-9]+" placeholder="UserID" ${mode=="update" ? "readonly='readonly' ":""}>
		<span data-placeholder="UserID"></span>	
		 
         <input type="password" name="userPwd" required="required" maxlength="15" pattern="[a-zA-Z0-9]+" placeholder="Password 1">
         <span data-placeholder="Password"></span>
    
         <input type="password" name="userPwdCheck" required="required" maxlength="15" pattern="[a-zA-Z0-9]+" placeholder="Password 2">
         <span data-placeholder="Password"></span>

         <input type="text" name="userName" value="${dto.userName}" required="required" maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="UserName" ${mode=="update" ? "readonly='readonly' ":""}>
         <span data-placeholder="UserName"></span> 
          
		 <span data-placeholder="Tel"></span>	

		 <select class="selectField" id="Tel1" name="Tel1" style="float: left;">
			<option value="">선 택</option>
			<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
			<option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
			<option value="016" ${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
			<option value="017" ${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
			<option value="018" ${dto.tel1=="018" ? "selected='selected'" : ""}>018</option>
			<option value="019" ${dto.tel1=="019" ? "selected='selected'" : ""}>019</option>
		 </select>		 
			<p style="float: left; margin-top: 12px;">&nbsp;-&nbsp;</p>
	     <input type="text" name="Tel2" value="${dto.Tel2}" required="required" maxlength="11" pattern="[0-9]+" placeholder="Tel" style="float:left; width: 142px;">
			<p style="float: left; margin-top: 12px;">&nbsp;-&nbsp;</p>
	     <input type="text" name="Tel3" value="${dto.Tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="Tel" style="float:left; width: 143px;">

		 <select name="selectEmail" onchange="changeEmail();" class="selectField" style="float: left;">
			<option value="">선 택</osption>
			<option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버</option>
			<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한메일</option>
			<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫메일</option>
			<option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지메일</option>
			<option value="direct">입력</option>
		 </select>
		 
         <input class="email" type="text" name="Email1" value="${dto.Email1}" required="required" size="13" maxlength="30" pattern="[a-zA-Z0-9]+" placeholder="Email" style="float:left; width: 140px;">
         	<p style="float: left; margin-top: 12px;">@</p>
         <input class="email" type="text" name="Email2" value="${dto.Email2}" required="required" size="13" maxlength="30" pattern="[a-zA-Z0-9]+" placeholder="Email" readonly="readonly" style="float:left; width: 150px;">
	     <span data-placeholder="Email"></span>	


	     <input type="text" name="userBirth" value="${dto.userBirth}" required="required" maxlength="10" pattern="[0-9]+" placeholder="Birth[ YYMMDD ]">
		 <span data-placeholder="Birth"></span>	

		<button class="indexBtn" type="button" name="sendButton" onclick="memberOk();"style="margin-left: 10px;">${mode=="created"?"sign up":"정보수정"}</button>
		<button class="indexBtn" type="reset">다시입력</button>
		<button class="indexBtn" type="button" onclick="javascript:location.href='<%=cp%>/';">${mode=="created"?"가입취소":"수정취소"}</button>

     </form>
   </div>   
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>