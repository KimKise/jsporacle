<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<script>

</script>
</head>
<body>
<h2>세션의 유효시간 설정</h2>
<%
	//톰캣의 세션 유효시간 : 1800초(30분)
	//유효시간 수정
	//	session.setMaxInactiveInterval(600);//10분
	int timeout = session.getMaxInactiveInterval();// 초단위
	out.println("세션의 유효시간"+timeout); 
	
%>
</body>
</html>