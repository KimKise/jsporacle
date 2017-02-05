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
	쿠키생성
	new Cookie("쿠키변수명","값")		--값은 스트링 타입만 가능, 한글은 인코딩 해야됨
*/
Cookie cookie = new Cookie("id", "김철수");
Cookie cookie2 = new Cookie("pwd", "1234");
//	쿠키의 유효시간 설정(초단위)	--1일(24*60*60)
cookie.setMaxAge(10);
cookie2.setMaxAge(10);
//	쿠키의 저장경로
cookie.setPath("/jsporacle");
//	쿠키의 도메인
cookie.setDomain("localhost");
//	쿠키가 클라이언트에 저장됨
response.addCookie(cookie);
response.addCookie(cookie2);
%>
쿠키가 생성되었습니다.<br>
<a href="useCookie.jsp">쿠키 확인</a>
</body>
</html>