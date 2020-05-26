<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
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
<link rel="stylesheet" href="<%=cp %>/resource/css/travel_created.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/resource/css/main.css" type="text/css">
<style type="text/css">

button{
	width: 100px; 
	height: 25px; 
	border-radius: 10px; 
	background: white;
	float: right;
	margin: 5px;
}
</style>

<script type="text/javascript">
    function sendOk(num) {
        var f = document.travelForm;

    	str = f.place.value;
        if(!str) {
            alert("장소를 입력하세요. ");
            f.place.focus();
            return;
        }
        
        str = f.information.value;
        if(!str) {
            alert("여행지 정보를 입력하세요. ");
            f.information.focus();
            return;
        }

   		f.action="<%=cp%>/travel/${mode}_ok.do?num="+num;

        f.submit();
    }
    
	<c:if test="${mode=='update'}">
	    function deleteFile(filename,num) {
	  	  var url="<%=cp%>/travel/deleteFile.do?filename="+filename+"&num="+num;
	  	  location.href=url;
	    }
	</c:if>
	
	
</script>
</head>
<body>

<div class="header">
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
	<div class="nav-bar">HOME > TRAVEL</div>
</div>

	<!-- 여행지   -->
	
	<div class=main>
		<form name="travelForm" method="post" enctype="multipart/form-data">
				<div class="content-box">
				<%-- 	<img alt="" src="<%=cp%>/uploads/travel/${dto.imageFilename}">--%>
				</div>
				<div class="content-box2">
					<img alt="" src="<%=cp %>/resource/img/user.png">
					<span style="padding: 5px;">&nbsp;작성자 ${dto.userId}(${dto.userName})</span><br><br>				
					<span>지&nbsp;&nbsp;&nbsp;역</span>
						<select name="type">
                           <option value="" value="${dto.type}">::지역선택::</option>
                           <option value="seoul" ${dto.type == "seoul" ? "selected='selected'" : ""}>seoul</option>
                           <option value="gangwon" ${dto.type == "gangwon" ? "selected='selected'" : ""}>gangwon</option>
                           <option value="chungcheon" ${dto.type == "chungcheon" ? "selected='selected'" : ""}>chungcheon</option>
                           <option value="jeonla" ${dto.type == "jeonla" ? "selected='selected'" : ""}>jeonla</option>
                           <option value="gyeongsang" ${dto.type == "gyeongsang" ? "selected='selected'" : ""}>gyeongsang</option>
                           <option value="jeju" ${dto.type == "jeju" ? "selected='selected'" : ""}>jeju</option>
                        </select>
					<span>장&nbsp;&nbsp;&nbsp;소 <input type="text" name="place" value="${dto.place}"></span>														
					<br><div class="content">정보 <br>
						<textarea name="information" style="width: 95%; height: 100px;">${dto.information}</textarea>
					</div>
					<input multiple="multiple" type="file" name="upload" style="margin-left: 70px;"><br><br>
					<c:forEach var="filename" items="${dto.imageFilename}">
					 		<span style="margin-left: 50px">&nbsp;&nbsp;</span>
					 		${filename}
					         <input type="hidden" value="${filename}"> 
					             | <a href="javascript:deleteFile('${filename}','${dto.num}');">삭제</a><br>
					</c:forEach>   
				</div>
				<br>
				<hr>
				<div class="button-box">
					<input type="hidden" name="type" value="${type}">
					<input type="hidden" name="imageFilename" value="${dto.imageFilename}"> 
					<button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/travel/list.do?type=${type}';">${mode=='update'?'수정취소':'등록취소'}</button>
					<button type="reset" class="btn">다시입력</button>
					<button type="button" class="btn" onclick="javascript:sendOk('${dto.num}');">${mode=='update'?'수정완료':'등록하기'}</button>
				</div>
			<br>
		</form>
	</div>

	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</div>

</body>
</html>