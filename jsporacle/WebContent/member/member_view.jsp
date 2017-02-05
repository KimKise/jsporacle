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

</script>
</head>
<body>
<form name="form1" method="post">
<table vorder="1">
	<tr>
		<td>아이디</td>
		<td>${dto.id}</td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="passwd" value="${dto.passwd}"></td>
	</tr>
	<tr>
		<td>이름</td>
		<td><input name="name" value="${dto.name}" ></td>
	</tr>
	<tr>
		<td>회원가입일자</td>
		<td>${dto.reg_date}</td>
	</tr>
	<tr>
		<td>주소</td>
		<td><input name="address" value="${dto.address}"></td>
	</tr>
	<tr>
		<td>전화</td>
		<td><input name="tel" value="${dto.tel}"></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="hidden" name="id" value="${dto.id}">
			<button type="button" id="btnUpdate">수정</button>
			<button type="button" id="btnDelete">삭제</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>