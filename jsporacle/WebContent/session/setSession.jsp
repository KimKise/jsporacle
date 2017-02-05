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
		JSP의 4가지 저장영역
		pageContext < request < session < application
	*/
	String id ="kim@nate.com";
	String passwd = "1234";
	int age = 20;
	//세션변수, 세션값
	session.setAttribute("id", id);
	session.setAttribute("passwd", passwd);
	session.setAttribute("age", age);
	out.println("세션에 값을 저장했습니다.");
%>
<a href="viewSession.jsp">세션 확인</a>
</body>
</html>