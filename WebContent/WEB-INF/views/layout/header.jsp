<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<script type="text/javascript">
//엔터 처리
$(function(){
	   $("input").not($(":button")).keypress(function (evt) {
	        if (evt.keyCode == 13) {
	            var fields = $(this).parents('form,body').find('button,input,textarea,select');
	            var index = fields.index(this);
	            if ( index > -1 && ( index + 1 ) < fields.length ) {
	                fields.eq( index + 1 ).focus();
	            }
	            return false;
	        }
	     });
});
</script>

	<div class="header-top">  
		<div class="header-center">
			<p style="margin: 2px;">
				<a href="<%=cp %>/main.do" style="text-decoration: none;">
				<img alt="HOME" src="<%=cp %>/resource/img/logo.jpg" width="37%" style="margin: 12px 16px; background-size: cover;" >
				</a>
			</p>
		</div>
		
		<div class="header-right">
			<div style="padding-top: 30px;  float: right;">
			<div class="menu">
		        <ul class="nav">
		            <li>
		                <a href="<%=cp%>/travel/list.do?type=seoul">여행지</a>
		                <ul>
		                    <li><a href="<%=cp%>/travel/list.do?type=seoul">수도권</a></li>
		                    <li><a href="<%=cp%>/travel/list.do?type=gangwon">강원</a></li>
		                    <li><a href="<%=cp%>/travel/list.do?type=chungcheon">충청</a></li>
		                    <li><a href="<%=cp%>/travel/list.do?type=jeonla">전라</a></li>
		                    <li><a href="<%=cp%>/travel/list.do?type=gyeongsang">경상</a></li>
		                    <li><a href="<%=cp%>/travel/list.do?type=jeju">제주</a></li>
                    
		                </ul>
		            </li>
		                
		            <li>
		                <a href="<%=cp %>/photo/photoMain.do">GALLERY</a>
		                <ul>
		                    <li><a href="<%=cp %>/photo/photoMain.do" style="padding-left: 100px;">사진</a></li>
		                </ul>
		            </li>
		
		            <li>
		                <a href="<%=cp %>/notice/notice.do">BOARD</a>
		                <ul>
		                	<li><a href="<%=cp %>/notice/notice.do"  style="padding-left: 190px;">공지사항</a></li>
		                	<li><a href="<%=cp%>/qna/list.do">Q&amp;A</a></li>
		                	<li><a href="<%=cp %>/board/board.do">자유게시판</a></li>
		                </ul>
		            </li>
		
		            <li>
		                <a href="<%=cp%>/contact/contact.do">CONTACT</a> 
		                <ul>
		                	<li><a href="<%=cp%>/contact/contact.do" style="padding-left: 275px;">CONTACT</a></li>
		                <c:if test="${sessionScope.member.userId=='admin'}">
		                	<li><a href="<%=cp%>/contact/list.do" >목록확인</a></li>
		                </c:if> 
		                </ul>   
		            </li>  
		            
		        </ul>
		        
		        <ul  class="nav2">
		            <li>
			            <c:if test="${empty sessionScope.member}">
			            	<a href="<%=cp%>/member/login.do" style="font-size: 12px;">login</a>
			            	&nbsp;
				       		<a href="<%=cp%>/member/member.do" style="font-size: 12px;">sign up</a>
				       	</c:if>	
				       	<c:if test="${not empty sessionScope.member}">
				       		<span style="color:blue;">${sessionScope.member.userName}</span>님
				       	 	&nbsp;
				       		<a href="<%=cp%>/member/logout.do" style="font-size: 12px;">Logout</a>
			            	&nbsp;	            
				       		<a href="<%=cp%>/member/pwd.do?mode=update" style="font-size: 12px;">${sessionScope.member.userId=="admin"?"관리자Page":"MyPage"}</a>
				     	</c:if>
			       	</li>	
		        </ul>   
    		</div>	   
		</div>
		</div>
	</div>
	
	


