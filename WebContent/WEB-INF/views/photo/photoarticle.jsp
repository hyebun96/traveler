<%@ page contentType="text/html; charset=UTF-8" %>
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
<link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/photo.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript">
function deletePhoto(photoNum) {
<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
    if(confirm("게시물을 삭제 하시겠습니까 ?")) {
    	 var url="<%=cp%>/photo/delete.do?photoNum="+photoNum+"&page=${page}";
    	 location.href=url;
    }	
</c:if>

<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
    alert("게시물을 삭제할 수  없습니다.");
</c:if>
}

function updatePhoto(photoNum) {
<c:if test="${sessionScope.member.userId==dto.userId}">
    var url="<%=cp%>/photo/update.do?photoNum="+photoNum+"&page=${page}";
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
	<div class="nav-bar">HOME > 갤러리</div>
</div>
	
<div class="photoarticle1">
    <div class="photoarticle2" style="width: 700px; margin: 100px 10px 10px 600px;">
        <font color="458B74" face="궁서체" size="16px" style="margin: 30px auto; text-align: center;"><i> Photo Content </i></font>
        
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse; border-top: 1px solid #4CAF50;">
			<tr height="40" style="border-top: 1px solid #4CAF50; border-bottom: 1px solid #4CAF50;">
			    <td colspan="2" align="center">
				  <b style="font-size: 16px;"> ${dto.subject}</b>
			    </td>
			</tr>
			
			<tr height="40" style="border-bottom: 1px solid #4CAF50;">
			    <td width="50%" align="left" style="padding-left: 5px; font-size: 16px;">
			      PhotoNum : ${dto.photoNum}
			    </td>
			    
			</tr>
	
			<tr>
			  <td colspan="2" align="left" style="padding: 10px 5px; margin: 50px auto;">
			      <img src="<%=cp%>/uploads/photo/${dto.imageFilename}" style="max-width:100%; height:300px; resize:both;">
			   </td>
			</tr>
						
			<tr style="border-bottom: 1px solid #4CAF50;" height="40">
			  <td colspan="2" align="left" style="padding: 0px 5px 20px; font-size: 16px; margin: 50px auto;" height="40">
			      ${dto.content}
			   </td>
			</tr>
			</table>
			
			<table style="width: 100%; margin: 40px auto 20px; border-spacing: 0px;">
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn1" onclick="updatePhoto('${dto.photoNum}');">수정</button>
			          <input type="hidden" name="photoNum" value="${dto.photoNum}">
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn1" onclick="deletePhoto('${dto.photoNum}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn1" onclick="javascript:location.href='<%=cp%>/photo/photoMain.do?page=${page}';">List</button>
			    </td>
			</tr>
			</table>
        </div>
        
    </div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>