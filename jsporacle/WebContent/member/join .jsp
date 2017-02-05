<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
$(document).ready(function(){
	$("#btnJoin").click(function(){
		var param="userid="+$("#userid").val()+"&passwd="+$("#passwd").val()+"&name="+$("#name").val();
		$.ajax({
			type : "post",
			url : "${path}/login_servlet/join.do",
			data : param,
			success : function(result){	//result를 받아서 result를 출력
				$("#result").html(result);
			}
		});
	});
});
</script>
</head>
<body>
<h2>회원가입과 로그인</h2>

아이디 : <input id="userid"><br>
비밀번호 : <input type="password" id="passwd"><br>
이름 : <input id="name"><br>
<button id="btnJoin">회원가입</button>

<div id="result"></div>
</body>
</html>