<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file = "../include/header.jsp" %>
<script>

</script>
</head>
<body>
<!-- 댓글 목록  -->
<table border="1" width="700px">
<c:forEach var="row" items="${list}">
	<tr>
		<td>
			${row.writer} ( <fmt:formatDate value="${row.reg_date}" pattern="yyyy-MM-dd HH:mm:ss" />)<br>
			${row.content}
		</td>
	</tr>
</c:forEach>
</table>
</body>
</html>