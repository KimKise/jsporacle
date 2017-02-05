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
	//session 부분삭제
	//session.removeAttribute("삭제할 변수명")
	//	session.removeAttribute("id");
	//	session.removeAttribute("passwd");
	
	//session 전체삭제
	session.invalidate();
%>
<a href="viewSession.jsp">세션 확인</a>
</body>
</html>