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
<link rel="stylesheet" href="<%=cp%>/resource/css/contact_list.css" type="text/css">
<script type="text/javascript">
function searchList() {
	var f=document.searchForm;
	f.action = "<%=cp%>/contact/list.do"
	f.submit();
}

function searchList2(ctSort) {
	var f=document.searchForm;
	f.ctSort.value=ctSort;
	f.action = "<%=cp%>/contact/list.do"
	f.submit();
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

<div class="board">
	<div>
		<h3>CONTACT 목록 확인</h3>
	</div>
	<div style="text-align: right;">
		<table>
			<tr>
				<td>
				<form name="searchForm" action="<%=cp%>/contact/list.do" method="post">
					<select name="condition" style="height: 23px;">
						<option value="ctSubject" ${condition=="ctSubject"?"selected='selected'":""}>글제목</option>
						<option value="ctContent" ${condition=="ctContent"?"selected='selected'":""}>글내용</option>
						<option value="ctName" ${condition=="ctName"?"selected='selected'":""}>작성자</option>
					</select>
					<input type="hidden" name="rows" value="${rows}">
					<input type="hidden" name="ctNum" value="${ctNum}">
					<input type="hidden" name="ctSort" value="${ctSort}">
					<input type="text" name="keyword" value="${keyword}">
					<button type="button" class="btn" onclick="searchList()">검색</button>
				</form>
				</td>
			</tr>
		</table>
		<table style="margin-bottom: 20px;">
			<tr>
				<td style="padding-right: 20px;"><a href="javascript:searchList2('sugg');">제안</a></td>
				<td style="padding-right: 20px;"><a href="javascript:searchList2('edit');">정보수정요청</a></td>
				<td style="padding-right: 20px;"><a href="javascript:searchList2('ad');">광고문의</a></td>
				<td style="padding-right: 20px;"><a href="javascript:searchList2('etc');">기타</a></td>
				<td><a onclick="javascript:location.href='<%=cp%>/contact/list.do';">새로고침</a></td>
			</tr>
		</table>
	</div>
	<div>
		<table  class="board-table">
			<tr  style="border-bottom: 2px solid black;">
				<td width="100">분류</td>
				<td width="50">번호</td>
				<td style="width: 50%">제목</td>
				<td>작성자</td>
				<td>작성일</td>
			</tr>
		<c:forEach var="dto" items="${list}">
			<tr style="background-color:${dto.fin==1 ? '#85C1E9 ':''}">
				<td>${dto.ctSort=='sugg' ? '제안':(dto.ctSort=='edit' ? '정보수정요청': (dto.ctSort=='ad'?'광고문의' : '기타'))}</td>
				<td>${dto.ctNum }</td>
				<td  class="board-title"><a href="${viewUrl}&ctNum=${dto.ctNum}">${dto.ctSubject}</a></td>
				<td>${dto.ctName }</td>
				<td>${dto.ctDate}</td>
				
			</tr>
		</c:forEach>
		</table>
			
		<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			<tr height="35">
				<td align="center">
			       ${dataCount==0?"등록된 게시물이 없습니다.":paging}
				</td>
	   		</tr>
		</table>		
	</div>
</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>