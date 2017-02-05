<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jstl(Jsp Standard Tag Library) 선언     
		prefic="태그 접두어" uri="태그의 식별자"
--> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
function view(id){
	//클릭한 사용자 아이디를 hidden태그에 복사
	document.form1.id.value = id;
	//컨트롤러 호출
	document.form1.submit();
}
</script>
</head>
<body>
<table border="1">
	<tr>
		<th>이름</th>
		<th>아이디</th>
		<th>비밀번호</th>
		<th>가입일자</th>
		<th>주소</th>
		<th>전화</th>
	</tr>
<!-- var="개별 값" items="전체 데이터"
	향상된 for문과 비슷 
	달라{개별값.name} 하면 getName()함수가 호출됨
  -->
<c:forEach var="row" items="${map.list}">
	<tr>
		<td><!-- view라는 자바스크립트 함수를 호출하면서 row.id 파라미터를 보냄  -->
			<a href="#" onclick="view('${row.id}')">${row.name}</a>
		</td>
		<td>${row.id}</td>
		<td>${row.passwd}</td>
		<td>${row.reg_date}</td>
		<td>${row.address}</td>
		<td>${row.tel}</td>
	</tr>
</c:forEach>
</table>
<form name="form1" method="post" action="${path}/member_servlet/view.do">
	<input type="hidden" name="id">
</form>
</body>
</html>