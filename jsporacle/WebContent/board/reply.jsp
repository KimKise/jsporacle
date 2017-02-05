<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file = "../include/header.jsp" %>
<script>
<c:if test="${param.message == 'error'}">
	alert("업로드 할 수 없는 파일입니다.");
</c:if>

$(document).ready(function(){
	$("#btnSave").click(function(){
			var writer=$("#writer").val();
			var subject = $("#subject").val();
			var content = $("#content").val();
			var passwd = $("#passwd").val();
			if(writer==""){
				alert("이름을 입력하세요");
				$("#writer").focus();
				return;
			}
			if(subject==""){
				alert("제목을 입력하세요");
				$("#subject").focus();
				return;
			}
			if(content==""){
				alert("내용을 입력하세요");
				$("#content").focus();
				return;
			}
			if(passwd==""){
				alert("비밀번호를 입력하세요");
				$("#passwd").focus();
				return;
			}
			document.form1.submit();
	});
});
</script>
</head>
<body>
<h2>답변쓰기</h2>
<form name="form1" method="post" action="${path}/board_servlet/insertReply.do">
<table border="1" width="700px">
	<tr>
		<td align="center">이름</td>
		<td><input name="writer" id="writer"></td>
	</tr>
	<tr>
		<td align="center">제목</td>
		<td><input name="subject" size="60" id="subject" value="Re: ${dto.subject}"></td>
	</tr>
	<tr>
		<td align="center">본문</td>
		<td><textarea rows="5" cols="60" name="content" id="content">${dto.content}</textarea></td>
	</tr>
	<tr>
		<td align="center">비밀번호</td>
		<td><input type="password" name="passwd" id="passwd"></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" value="확인" id="btnSave">
			<!-- 게시물 번호 -->
			<input type="hidden" name="num" value="${dto.num}">
		</td>
	</tr>
</table>
</form>
</body>
</html>