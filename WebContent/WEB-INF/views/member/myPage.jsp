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

function memberOk(mode) {
	var f = document.memberForm;
	var str;

    if(mode=="delete") {
    	alert("탈퇴하시겠습니까?");
    	f.action = "<%=cp%>/member/delete.do";
   
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
<div class="navigation">
	<div class="nav-bar">HOME > MyPage</div>
</div>


<div class="main">
	<div class="signup">
		<h1>${title}<img style="width:70px; height: 70px; float: right;" src="<%=cp%>/uploads/travel/${dto.imageFilename}" />
		</h1>	
	</div>
	
	<div class="index">

	  <form name="memberForm" action="javascript:send();" method="post" enctype="multipart/form-data">

<!-- 파일처리하려함. 아직 못함. -->
<%-- 	  	<input type="file" name="upload" accept="image/*" class="btn" size="53" style="height: 25px;">
			<c:if test="${not empty dto.imageFilename}">
				${dto.imageFilename}
				| <a href="javascript:deleteFile('${dto.num}')">삭제</a>
			</c:if> --%>

		
		 <p>
		 	<font>&nbsp;&nbsp;&nbsp;&nbsp;I&nbsp;D&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
		 	<input type="text" name="userId" value="${dto.userId}" class="imo" readonly="readonly" style="width: 80%"/> 
		</p>
		<p>
		 	<font>&nbsp;&nbsp;이&nbsp;름&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
         	<input type="text" name="userName" value="${dto.userName}" readonly="readonly" style="width: 80%"/>
        </p>	
		<p>
		 	<font>전화번호&nbsp;&nbsp;</font>
	     	<input type="text" name="tel" value="${dto.tel1} - ${dto.tel2} - ${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" readonly="readonly" style="width: 80%"/>
		    
		    <input type="hidden" name="tel1" value="${dto.tel1}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" style="float:left; width: 120px;" readonly="readonly" >
		    <input type="hidden" name="tel2" value="${dto.tel2}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" style="float:left; width: 120px;" readonly="readonly" >
		    <input type="hidden" name="tel3" value="${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" style="float:left; width: 120px;" readonly="readonly" >
		</p>
		<p>
		 	<font>&nbsp;&nbsp;e-mail&nbsp;&nbsp;&nbsp;</font>
         	<input class="email" type="text" name="email1" value="${dto.email1} @ ${dto.email2}" required="required" size="13" readonly="readonly" style="width: 80%"/>
         
         	<input class="email" type="hidden" name="email1" value="${dto.email1}" readonly="readonly" style="float:left; width: 150px;">
         	<input class="email" type="hidden" name="email2" value="${dto.email2}" readonly="readonly" style="float:left; width: 150px;">
        </p> 	
 		<p>
		 	<font>생년월일&nbsp;&nbsp;</font>	
			<input type="text" name="userBirth" value="${dto.userBirth}" readonly="readonly" style="width: 80%"/>
		</p>
		
		<button class="indexBtn" type="button" name="sendButton" onclick="memberOk('update');" style="margin-left: 100px;">정보수정</button>
		<c:if test="${sessionScope.member.userId=='admin'}">
		<a href="<%=cp%>/member/list.do"><button class="indexBtn" type="button" name="sendButton">회원리스트</button></a>
	 	</c:if>
		<c:if test="${sessionScope.member.userId!='admin'}">
		<a href="<%=cp%>/member/delete.do"><button class="indexBtn" type="button" name="sendButton" onclick="memberOk('delete');">회원탈퇴</button></a>
	 	</c:if>
     </form>
   </div>   
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>