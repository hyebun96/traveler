<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
// 비밀번호 미입력시 경고창
function checkValue(){
    if(!document.deleteform.password.value){
        alert("비밀번호를 입력하지 않았습니다.");
        return false;
    }
}
</script>
</head>
<body>

 <form name="deleteform" method="post" action="MainForm.jsp?contentPage=member/pro/DeletePro.jsp"
        onsubmit="return checkValue()">
 
        <table>
            <tr>
                <td bgcolor="skyblue">비밀번호</td>
                <td><input type="password" name="password" maxlength="50"></td>
            </tr>
        </table>
        
        <br> 
        <input type="button" value="취소" onclick="javascript:window.location='MainForm.jsp'">
        <input type="submit" value="탈퇴" /> 
    </form>



</body>
</html>