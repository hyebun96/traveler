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

<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
$(function() {  //엔터치면 다음으로 넘어가게함.
	$("input").not($(":button")).not($(":reset")).keypress(function(evt) { //input에서 keypress가 발생했으면(버튼과 리셋은 빼고) 
		if(evt.keyCode==13){
			var fields=$(this).parents("form,body").find("button,input,select,textarea"); //form또는 body 안에 모든 button,input,select,textarea 찾아라
			var index=fields.index(this);
			
			if(index>-1 && (index+1)<fields.length){
				fields.eq(index+1).focus();
			}
			return false; //엔터 이벤트 취소
		}
	});
	
});
</script>

<script type="text/javascript"> /* 자바스크립트는  웹브라우저에 의해 실행되어지는거,전세계적으로 많이 씀*/

function memberOk() {
	var f = document.memberForm;
	var str;

    var mode="${mode}";
    if(mode=="delete") {
    	alert("탈퇴하시겠습니까?");
    	f.action = "<%=cp%>/member/member_ok.do";
   
    } else if(mode=="update") {
    	alert("수정하시겠습니까?");
    	f.action = "<%=cp%>/member/update.do";
    }

    f.submit();
}


</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>


<div class="main">
	<div class="signup">
		<h1>${title}</h1>	
		<br>
	</div>
	
	<div class="index">

	  <form name="memberForm" action="javascript:send();" method="post" enctype="multipart/form-data">

<!-- 파일처리하려함. 아직 못함. -->
	  	<input type="file" name="upload" accept="image/*" class="btn" size="53" style="height: 25px;">
			<c:if test="${not empty dto.saveFilename}">
				${dto.originalFilename}
				| <a href="javascript:deleteFile('${dto.num}')">삭제</a>
			</c:if>


		 <input type="text" name="userId" value="${dto.userId}" class="imo" readonly="readonly"> 

         <input type="text" name="userName" value="${dto.userName}" readonly="readonly">
          
		 <span data-placeholder="tel"></span>
			<p style="float: left; margin-top: 12px;">&nbsp;-&nbsp;</p>
	     <input type="text" name="tel2" value="${dto.tel2}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" style="float:left; width: 142px;" readonly="readonly" >
			<p style="float: left; margin-top: 12px;">&nbsp;-&nbsp;</p>
	     <input type="text" name="tel3" value="${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" style="float:left; width: 143px;" readonly="readonly" >

         <input class="email" type="text" name="email1" value="${dto.email1}" required="required" size="13" style="float:left; width: 140px;" readonly="readonly" >
         	<p style="float: left; margin-top: 12px;">@</p>
         <input class="email" type="text" name="email2" value="${dto.email2}" readonly="readonly" style="float:left; width: 150px;">
	     <span data-placeholder="Email"></span>	


	     <input type="text" name="userBirth" value="${dto.userBirth}" readonly="readonly" >

		<button class="indexBtn" type="button" name="sendButton" onclick="memberOk();"style="margin-left: 10px;">정보수정</button>
		<button class="indexBtn" type="reset">회원탈퇴</button>
	 	
	 		
	 	<%-- 	
	 		<c:if test="${mode=='update'}">
				<input type="hidden" name="num" value="${dto.num}">
				<input type="hidden" name="fileSize" value="${dto.filesize}">
				<input type="hidden" name="saveFilename" value="${dto.saveFilename}">
				<input type="hidden" name="originalFilename" value="${dto.originalFilename}">
			</c:if> 

			<input type="hidden" name="rows" value="${rows}">
		 --%>
	
     </form>
   </div>   
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>