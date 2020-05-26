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
<link rel="stylesheet" href="<%=cp %>/resource/css/contact_view.css" type="text/css">
<script type="text/javascript">
	function deleteContact(ctNum) {
		<c:if test="${sessionScope.member.userId=='admin'}">
		    var query = "ctNum="+ctNum+"&${query}";
		    var url = "<%=cp%>/contact/delete.do?" + query;
	
		    if(confirm("자료를 삭제 하시 겠습니까 ? "))
		    	location.href=url;
		</c:if>	    	
	}
	
	function finContact(ctNum) {
		<c:if test="${sessionScope.member.userId=='admin'}">
		    var query = "ctNum="+ctNum+"&${query}";
		    var url = "<%=cp%>/contact/update.do?"+query;
	
		    if(confirm("완료처리 하시 겠습니까 ? "))
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
	<div class="nav-bar">HOME > CONTACT목록확인</div>
</div>


<div class="viewboard">
		<table class="view-table">
			<tr style="border-top: 2px solid black;">
				<td class="viewbox">작성자</td>
				<td class="viewcontent">${dto.ctName}</td>
			</tr>
			<tr>
				<td class="viewbox">전화번호</td>
				<td class="viewcontent">${dto.ctTel}</td>
			</tr>
			<tr>
				<td class="viewbox">이메일</td>
				<td class="viewcontent">${dto.ctEmail}</td>
			</tr>
			<tr>
			
				<td class="viewbox">작성시간</td>
				<td class="viewcontent">${dto.ctDate}</td>
			</tr>
			<tr>
				<td class="viewbox">제목</td>
				<td class="viewcontent">${dto.ctSort=='sugg' ? '제안':(dto.ctSort=='edit' ? '정보수정요청': (dto.ctSort=='ad'?'광고문의' : '기타'))}| ${dto.ctSubject}</td>
			</tr>			
			<tr>
				<td class="viewbox" style="height: 200px;">내용</td>
				<td style="height: 200px;">${dto.ctContent }</td>

			</tr>
			
		</table>
		
		<div align="right">
			<button type="button" onclick="javascript:location.href='<%=cp%>/contact/list.do?${query}';">목록으로</button>		
			<c:if test="${dto.fin==0}">		    
			<button type="button" class="btn" onclick="finContact('${dto.ctNum}');">완료처리</button>
			</c:if>	
			<button type="button" class="btn" onclick="deleteContact('${dto.ctNum}');">삭제</button>
				<input type="hidden" name="ctNum" value="${dto.ctNum}">
				<input type="hidden" name="page" value="${page}">	
			<input type="hidden" name="rows" value="${rows}">
		</div>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>	
</body>
</html>