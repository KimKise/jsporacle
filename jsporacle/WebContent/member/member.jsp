<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file ="../include/header.jsp" %>
<script>
$(document).ready(function(){
	list();
	$("#btnSave").click(function(){
		insert();
	});
});
function list(){
	$.ajax({
		type : "post",
		url : "${path}/member_servlet/list.do",
		success : function(result){
			$("#memberList").html(result);
		}
	});
}
function insert(){
	var param = "id="+$("#id").val()+"&passwd="+$("#passwd").val()+"&name="+$("#name").val()
				+"&address="+$("#address").val()+"&tel="+$("#tel").val();
	$.ajax({
		type : "post",
		url : "${path}/member_servlet/insert.do",
		data : param,
		success: function(){
			list();
		}
		
	});
}
</script>
</head>
<body>
아이디 <input id="id">
비번 <input type="password" id="passwd"><br>
이름 <input id="name"><br>
주소 <input id="address">
전화 <input id="tel">
<button id="btnSave">추가</button>
<div id="memberList"></div>
</body>
</html>