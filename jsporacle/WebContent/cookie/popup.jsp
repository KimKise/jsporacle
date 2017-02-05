<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<script>
$(document).ready(function(){
	$("#nopopup").click(function(){
		var now = new Date();
		var val="";
		now.setDate(now.getDate());	//현재 날짜 셋팅
		if(this.checked == true){	//여기서 this는 $("#nopopup")을 가리킴
			val = "N";	//7일간 팝업 안함
		}else{
			val = "Y";	//7일간 팝업 함
		}
		setCookie("showCookise", val, 7);
	});
});

function setCookie(cname, cvalue, days){
	var d = new Date();
	d.setTime(d.getTime() + (days*24*60*60*1000)); //오늘날짜 + 유지할 쿠키 시간
	var expires = "expires="+d.toGMTString();
	document.cookie = cname+"="+cvalue+";"+expires;
	window.close();//현재 창 닫기
}
</script>
</head>
<body>
<input type="checkbox" id="nopopup"> 7일간 보이지 않기
</body>
</html>