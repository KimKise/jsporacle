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
		쿠키 삭제
		new Cookie("쿠키변수명",""); 
	*/
	Cookie cookie = new Cookie("id", "");
	cookie.setMaxAge(0);	//즉시 삭제
	//cookie.setMaxAge(-1);	//브라우저를 닫을 때 삭제
	response.addCookie(cookie);
%>
	쿠키가 삭제되었습니다.
	<a href="useCookie.jsp">쿠키 확인</a>
</body>
</html>