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
<script type="text/javascript">
function sendOk() {
    var f = document.contactForm;
    
	var str = f.ctName.value;
    if(!str) {
        alert("이름을 입력하세요. ");
        f.ctName.focus();
        return;
    }
    
    if(f.ctTel.value==null && f.ctEmail.value==null) {
        alert("이메일, 전화번호 중 연락처를 한 개 이상입력하세요. ");
        f.ctEmail.focus();
        return;
    }

	str = f.ctSubject.value;
    if(!str) {
        alert("제목을 입력하세요. ");
        f.ctSubject.focus();
        return;
    }
    
	str = f.ctSort.value;
    if(!str) {
        alert("분류를 선택하세요. ");
        f.ctSort.focus();
        return;
    }

	str = f.ctContent.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.ctContent.focus();
        return;
    }
    
    if(confirm("자료를 전송하시겠습니까 ? ")){
		f.action="<%=cp%>/contact/contact_ok.do";
    	
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
	<div class="nav-bar">HOME > CONTACT</div>
</div>


<div class="member-body">
	<div class="title">
		<h1>CONTACT</h1>
	</div>	
	<form name="contactForm" method="post">
		<ul class="list-row">
			<li class="col-sub">이름</li>
			<li class="col-input">
				<input type="text" name=ctName class="boxTF">
			</li>
		</ul>
			
		<ul class="list-row">
			<li class="col-sub">이메일</li>
			<li class="col-input">
				<input type="text" name=ctEmail class="boxTF">
			</li>
		</ul>
			
		<ul class="list-row">
			<li class="col-sub">전화번호</li>
			<li class="col-input">
				<input type="text" name=ctTel class="boxTF">
			</li>
		</ul>
			
		<ul class="list-row" style="clear: both;">
			<li class="col-sub">분류</li>
			<li class="col-input">
				  <select name="ctSort" class="selectField">
						<option value="">::선 택::</option>
						<option value="sugg" ${dto.ctSort=="sugg" ? "selected='selected'" : ""}>제안</option>
						<option value="edit"  ${dto.ctSort=="edit" ? "selected='selected'" : ""}>정보수정요청</option>
						<option value="ad" ${dto.ctSort=="ad" ? "selected='selected'" : ""}>광고문의</option>
						<option value="etc" ${dto.ctSort=="etc" ? "selected='selected'" : ""}>기타</option>
				  </select>				
			</li>
		</ul>
		<ul class="list-row" style="clear: both;">
			<li class="col-sub">제목</li>
			<li class="col-input2">
				<input type="text" name=ctSubject class="boxTF">
			</li>
		</ul>
		<ul class="list-row" style="clear: both;">
			<li class="col-sub">내용</li>
		</ul>
		<ul class="list-row" style="clear: both;">
			<li style="margin-bottom: 100px;">
				<textarea id="content" name="ctContent" rows="15" cols="54" style="resize: none;" ></textarea></li>	
			<li class="col-btn" style="clear: both; margin-top: 250px;" >
				<button type="button" class="btn" onclick="sendOk();"> 전송 </button>
				<button type="reset" class="btn"> 다시입력 </button>
			</li>
		</ul>
	</form>
</div>
	
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>


</body>
</html>