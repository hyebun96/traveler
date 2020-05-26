<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
<link rel="stylesheet" href="<%=cp %>/resource/css/viewboard.css" type="text/css">

<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript">
function deleteBoard(num) {
	
	

	<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId == dto.id}">
	    var query = "num="+num+"&${query}";
	    var url = "<%=cp%>/board/delete.do?" + query;

	    if(confirm("자료를 삭제 하시 겠습니까 ? "))
	    	location.href=url;
	</c:if>
	
	<c:if test="${sessionScope.member.userId!='admin' &&  sessionScope.member.userId != dto.id}">
    var query = "num="+num+"&${query}";
    var url = "<%=cp%>/board/access.do?" + query;
    location.href=url;
	</c:if>
	
	
	
	    
	
	}

	function updateBoard(num) {
		<c:if test="${sessionScope.member.userId==dto.id}">
	    var page = "${page}";
	    var query = "num="+num+"&page="+page;
	    var url = "<%=cp%>/board/update.do?" + query;

	    location.href=url;
	    </c:if>

	    <c:if test="${sessionScope.member.userId!=dto.id}">
	    var query = "num="+num+"&${query}";
	    var url = "<%=cp%>/board/access.do?" + query;
	    location.href=url;
	 	</c:if>
	   
	
	}



</script>
</head>
<body>
<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > 자유게시판</div>
</div>

<div class="viewboard">


		<table class="view-table">
			<tr style="border-top: 2px solid black;">
				<td class="viewbox">제목</td>
				<td class="viewcontent">${dto.title }</td>
				<td class="viewbox">조회수</td>
				<td class="viewcontent">${dto.viewCount }</td>
			</tr>
			
			<tr>
				<td class="viewbox">작성자</td>
				<td class="viewcontent">${dto.name }</td>
				<td class="viewbox">작성시간</td>
				<td class="viewcontent">${dto.created }</td>
			</tr>
			<tr>
				<td class="viewbox" style="height: 200px;">내용</td>
				<td rowspan="3" style="height: 200px;">${dto.content }</td>
				<td></td>
				<td></td>
			</tr>
			
			
			
		</table>
		
		<div align="right">
			<button type="button" onclick="javascript:location.href='<%=cp%>/board/board.do?${query}';">목록으로</button>
							    
			<button type="button" class="btn" onclick="updateBoard('${dto.num}');">수정</button>
			
							    
			<button type="button" class="btn" onclick="deleteBoard('${dto.num}');">삭제</button>
			
		</div>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>	
</body>
</html>