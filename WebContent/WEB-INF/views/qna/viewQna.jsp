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
<link rel="stylesheet" href="<%=cp %>/resource/css/qna_view.css" type="text/css">

<script type="text/javascript">

function deleteQna(qnaNum) {
	<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
	    var query = "qnaNum="+qnaNum+"&${query}";
	    var url = "<%=cp%>/qna/delete.do?" + query;

	    if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
	    	location.href=url;
	</c:if>    
	<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
	    alert("게시물을 삭제할 수  없습니다.");
	</c:if>
	}

function updateQna(qnaNum) {
	<c:if test="${sessionScope.member.userId==dto.userId}">
	    var page = "${page}";
	    var query = "qnaNum="+qnaNum+"&page="+page;
	    var url = "<%=cp%>/qna/update.do?" + query;

	    location.href=url;
	</c:if>

	<c:if test="${sessionScope.member.userId!=dto.userId}">
	   alert("게시물을 수정할 수  없습니다.");
	</c:if>
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

<div class="viewqna">
	<h3>Q & A</h3>
		<table class="view-table">
			<tr style="border-top: 2px solid black;">
				<td class="viewbox">제목</td>
				<td class="viewcontent">${dto.subject}</td>
				<td class="viewbox">조회수</td>
				<td class="viewcontent">${dto.hitCount}</td>
			</tr>
			
			<tr>
				<td class="viewbox">작성자</td>
				<td class="viewcontent">${dto.userName}</td>
				<td class="viewbox">작성시간</td>
				<td class="viewcontent">${dto.qnaDate }</td>
			</tr>
			<tr style="height: 400px;">
				<td class="viewbox">내용</td>
				<td colspan="3">${dto.content}</td>

			</tr>		
		</table>
		
		<div align="right">
			<button type="button" onclick="javascript:location.href='<%=cp%>/qna/list.do?${query}';">목록으로</button>
			<c:if test="${sessionScope.member.userId=='admin' && dto.depth==0}">
				<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/qna/reply.do?qnaNum=${dto.qnaNum}&page=${page}';">답변</button>
			</c:if>
			<c:if test="${sessionScope.member.userId == dto.userId}">			    
				<button type="button" class="btn" onclick="updateQna('${dto.qnaNum}');">수정</button>
			</c:if>	
			<c:if test="${sessionScope.member.userId != dto.userId}">
			    <button type="button" class="btn" disabled="disabled" hidden="hidden">수정</button>
			</c:if>				    
			<button type="button" class="btn" onclick="deleteQna('${dto.qnaNum}');">삭제</button>			
		</div>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>