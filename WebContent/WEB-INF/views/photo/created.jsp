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
<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/photo.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>
<script type="text/javascript">
    function sendPhoto() {
        var f = document.photoForm;

    	var str = f.subject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.subject.focus();
            return;
        }

    	str = f.content.value;
        if(!str) {
            alert("설명을 입력하세요. ");
            f.content.focus();
            return;
        }

        var mode="${mode}";
    	  if(mode=="created"||mode=="update" && f.upload.value!="") {
    		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload.value)) {
    			alert('이미지 파일만 가능합니다. !!!');
    			f.upload.focus();
    			return;
    		}
    	  }
    	  
      	  if(mode=="created")
        		f.action="<%=cp%>/photo/created_ok.do";
      	  else if(mode=="update")
        		f.action="<%=cp%>/photo/update_ok.do";

        f.submit();
    }
    
    function imageViewer(img) {
    	var viewer = $("#imagePhotoLayout");
    	var s="<img src='"+img+"' width=570 height=450>";
    	viewer.html(s);
    	
    	$("#dialog").dialog({
    		title:"이미지 확대",
    		width: 600,
    		height: 520,
    		modal: true
    	});
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
<div class="photocreated1">
    <div class="photocreated2" style="width: 700px; margin: 100px 10px 10px 600px;">
        	<font color="458B74" face="궁서체" size="16px" style="margin: 30px auto; text-align: center;"><i> Photo </i></font>
 			<form name="photoForm" method="post" enctype="multipart/form-data">
			  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse; border-top: 1px solid #4CAF50;">
			  	
			  <tr align="left" height="40" style=" border-bottom: 1px solid #4CAF50;">  
			      <td width="100" style="text-align: center; border-right: 1px solid #4CAF50;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
			      <td style="padding-left:10px;"> 
			        <input type="text" name="subject" maxlength="100" class="boxTF" style="width: 95%; " value="${dto.subject}">
			      </td>
			  </tr>
			
			  <tr align="left" height="40" style="border-bottom: 1px solid #4CAF50;"> 
			      <td width="100" style="text-align: center; border-right: 1px solid #4CAF50;">작성자</td>
			      <td style="padding-left:10px;"> 
			            ${sessionScope.member.userName}
			      </td>
			  </tr>
			
			  <tr align="left" style="border-bottom: 1px solid #4CAF50;"> 
			      <td width="100" style="text-align: center; padding-top:5px; border-right: 1px solid #4CAF50;" valign="top">설&nbsp;&nbsp;&nbsp;&nbsp;명</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;"> 
			        <textarea name="content" rows="12" class="boxTA" style="width: 95%;">${dto.content}</textarea>
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-bottom: 1px solid #4CAF50;">
			      <td width="100" style="text-align: center; border-right: 1px solid #4CAF50;">이미지</td>
			      <td style="padding-left:10px;"> 
			           <input type="file" name="upload" accept="image/*"
			                      class="boxTF" size="53" style="height: 25px;">
			       </td>
			  </tr> 

			  <c:if test="${mode=='update'}">
				  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
				      <td width="100" bgcolor="#eeeeee" style="text-align: center;">등록이미지</td>
				      <td style="padding-left:10px; "> 
				         <img src="<%=cp%>/uploads/photo/${dto.imageFilename}"
				                     width="30" height="30" border="0"
				                     onclick="imageViewer('<%=cp%>/uploads/photo/${dto.imageFilename}');"
				                     style="cursor: pointer;">
				       </td>
				  </tr> 
			  </c:if>			  
			  </table>
			
			  <table style="width: 100%; margin: 30px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			        <button type="button" class="btn1" onclick="sendPhoto();">${mode=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="btn1">다시입력</button>
			        <button type="button" class="btn1" onclick="javascript:location.href='<%=cp%>/photo/photoMain.do';">${mode=='update'?'수정취소':'등록취소'}</button>
			         <c:if test="${mode=='update'}">
                             <input type="hidden" name="photoNum" value="${dto.photoNum}">
                             <input type="hidden" name="userId" value="${dto.userId}">
                             <input type="hidden" name="imageFilename" value="${dto.imageFilename}">
                             <input type="hidden" name="page" value="${page}">
			        </c:if>
			      </td>
			    </tr>
			  </table>
			</form>
        </div>
        
    </div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<div id="dialog">
      <div id="imagePhotoLayout"></div>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>