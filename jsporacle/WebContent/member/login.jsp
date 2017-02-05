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
	$("#btnLogin").click(function(){
		var param ="userid="+$("#userid").val()+"&passwd="+$("#passwd").val();
		$.ajax({
			type :"post",
			url : "${path}/login_servlet/login.do",
			data : param,
			success : function(result){
				$("#result").html(result);
			}
		});
	});
});
</script>
</head>
<body>
아이디 : <input id="userid"><br>
비번 : <input type="password" id="passwd"><br>
<button id="btnLogin">로그인</button>
<div id="result"></div>
</body>
</html>