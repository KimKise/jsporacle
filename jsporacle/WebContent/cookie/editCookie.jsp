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
<%	
	/*
		쿠키 수정
		addCookie()
	*/
	response.addCookie(new Cookie("id", "park"));
%>
쿠키가 변경되었습니다.<br>
<a href="useCookie.jsp">쿠키 확인</a>
</body>
</html>