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
<!-- action 속성이 없으면 현재 페이지로 값을 전달함  -->
<form method="post">
	이름 : <input name="name">
	<input type="submit" value="확인">
</form>
<%
	String name = request.getParameter("name");
	out.println("이름: "+name);
%>
이름 : ${param.name}
</body>
</html>