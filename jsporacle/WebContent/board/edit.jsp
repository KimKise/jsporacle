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


$(document).ready(function(){
	$("#btnUpdate").click(function(){
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
			//첨부파일 이름
			var filename = form1.file1.value;
			var start = filename.lastIndexOf(".")+1;
			if(start != -1){
				var ext = filename.substring(start, filename.length);
				if(ext=="jsp" || ext=="exe"){
					alert("업로드할 수 없는 파일입니다.");
					return;
				}
			}
			document.form1.action="${path}/board_servlet/update.do?num=${dto.num}";
			document.form1.submit();
	});
});
</script>
</head>
<body>
<h2>수정/삭제</h2>
<form name="form1" method="post" enctype="multipart/form-data">
<table border="1" width="700px">
	<tr>
		<td align="center">이름</td>
		<td><input name="writer" id="writer" value="${dto.writer}"></td>
	</tr>
	<tr>
		<td align="center">제목</td>
		<td><input name="subject" size="60" id="subject" value="${dto.subject}"></td>
	</tr>
	<tr>
		<td align="center">본문</td>
		<td><textarea rows="5" cols="60" name="content" id="content">${dto.content}</textarea></td>
	</tr>
	<tr>
		<td align="center">첨부파일</td>
		<td>
			<c:if test="${dto.filesize > 0 }">
				${dto.filename}( ${dto.filesize} bytes )
				<input type="checkbox" name="fileDel">
				첨부파일 삭제<br>
			</c:if>
			<input type="file" name="file1">
		</td>
	</tr>
	<tr>
		<td align="center">비밀번호</td>
		<td><input type="password" name="passwd" id="passwd"></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="hidden" name="num" value="${dto.num}">
			<input type="button" value="수정" id="btnUpdate">
			<input type="button" value="삭제" id="btnDelete">
		</td>
	</tr>
</table>
</form>
</body>
</html>