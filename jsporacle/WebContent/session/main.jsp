<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/session_check.jsp" %>
<script>
$(document).ready(function(){
	$("#btnLogout").click(function(){
		//페이지 이동
		location.href="${path}/session_servlet/logout.do";
	});
});
</script>
</head>
<body>
<h2><%=session.getAttribute("message")%></h2>
<%=session.getAttribute("userid") %>님이 접속중입니다.

<h2>${sessionScope.message}</h2>
${sessionScope.userid}님이 접속중입니다.

<br>
<button type ="button" id="btnLogout">로그아웃</button>
</body>
</html>