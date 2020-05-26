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
<link rel="stylesheet" href="<%=cp %>/resource/css/write.css" type="text/css">


<script type="text/javascript">
function sendOk() {
    var f = document.writeQnaForm;

	var str = f.subject.value;
    if(!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

	str = f.content.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

		f.action="<%=cp%>/qna/${mode}_ok.do";

    f.submit();
}
</script>
</head>
<body>
<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > Q&A</div>
</div>
<div class="write">
	<h3>Q & A</h3>
	<h3 style="text-align: center; margin: 0 auto;">글쓰기</h3>
	<form name="writeQnaForm" method="post">
	<div>
		<label>글제목<br>
			<input name = "subject" class="write-title" type="text" value="${dto.subject}">
		</label>
		<br>
		
		<label>내용<br>
			<textarea name="content" rows="15" cols="112" style="resize: none;" >${dto.content}</textarea>
		</label>

	</div>
	<c:if test="${mode=='update'}">
		<input type="hidden" name="qnaNum" value="${dto.qnaNum}">
		<input type="hidden" name="page" value="${page}">
	</c:if>


	<div style="text-align: right;">
		<c:if test="${mode=='update'}">
			 <input type="hidden" name="qnaNum" value="${dto.qnaNum}">
			 <input type="hidden" name="page" value="${page}">
			 <input type="hidden" name="condition" value="${condition}">
			 <input type="hidden" name="keyword" value="${keyword}">
		</c:if>
		<c:if test="${mode=='reply'}">
			  <input type="hidden" name="groupNum" value="${dto.groupNum}">
			  <input type="hidden" name="depth" value="${dto.depth}">
			  <input type="hidden" name="parent" value="${dto.qnaNum}">
			  <input type="hidden" name="page" value="${page}">

		</c:if>
		<button type="button" onclick="sendOk();">${mode=='update'?'수정':'등록'}</button>
		<button type="button" onclick="javascript:location.href='<%=cp%>/qna/list.do';">목록으로</button>
	</div>
	</form>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>