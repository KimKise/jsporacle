<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="java.util.Date"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
$(document).ready(function(){
	//팝업창 띄우기
	//팜업창 열기 옵션값을 가져옴
	var show = getCookie("showCookies");
	if(show != "N"){//N이면 열지 않음
	//window.open(url, id, option);
		window.open("popup.jsp","","width=300, height=400");	
	}
	
	

	//userid에 대한 쿠키 조회
	var cookie_userid = getCookie("userid");
	//저장된 쿠키가 있으면
	if(cookie_userid != ""){
		//userid 태그에 쿠키값을 입력
		$("#userid").val(cookie_userid);
		//첵크박스를 체크상태로 설정
		$("#chkSave").attr("checked",true);
	}
	
	//로그인 버튼 클릭
	$("#btnLogin").click(function(){
		if($("#chkSave").is(":checked")){ //check상태
			//사용자가 입력한 userid를 쿠키에 저장
			saveCookie($("#userid").val());	
		}else{			//첵크 해제이면 쿠키값 해제
			saveCookie("");
		}
	});
	//아이디 저장 클릭
	$("#chkSave").click(function(){
		//태그.is(":속성")
		//if($("#chkSave").attr("checked")){
		if($("#chkSave").is(":checked")){//태그의 속성 값의 책크 상태는 무엇입니까?
			if(confirm("로그인 정보를 저장하시겠습니까?")){
				//태그.attr("속성", 속성값)
				//태그의 속성값을 설정
				//attr은 html요소,  prop javascript요소
				//$("#chkSave").attr("checked",true);
				$("#chkSave").prop("checked",true);
			}else{
				$("#chkSave").prop("checked",false);
			}
		}
	});
});

function saveCookie(id){
	if(id!=""){
		setCookie("userid", id, 7);	//7일
	}else{
		setCookie("userid", id, -1); //삭제
	}
}
//setCookie(쿠키변수명, 쿠키값, 유효날짜)
function setCookie(name, value, days){
	var today = new Date();
	//today.getDate() 오늘 날짜
	today.setDate(today.getDate() + days);
	//document.cookie 쿠키를 셋팅
	//	쿠키변수명 = 쿠키값;path=저장경로;expires=만료일자
	//	userid=kim;path=/;expires=날짜;
	document.cookie=name+"="+value+";path=/;expires="+today.toGMTString()+";";
	console.log(document.cookie);
	alert(document.cookie);
}
function getCookie(cname){
	var cookie = document.cookie+";"; //브라우저에 저장된 쿠키정보를 가져옴
	var idx=cookie.indexOf(cname,0); //cookie전체 문자열에서 0번부터 시작해서 userid를 찾는다.
	var val="";
	//쿠키값은 이렇게 나옴 : userid=kim;Max-Age=10;domain=localhost;path=/jsporacle
	//여기서 userid=kim를 뽑아내기 위해
	if(idx != -1){ //-1이 아니면 userid라는 쿠키가 있다.
		// substring(start, length) 시작 인덱스에서 몇번째 까지 문자열을 가져옴
		cookie=cookie.substring(idx, cookie.length);
		begin = cookie.indexOf("=",0)+1;
		end = cookie.indexOf(";",begin);
		val = cookie.substring(begin, end);
	}
	return val;
}
</script>
</head>
<body>
<form method="post" name="form1" action="${path}/cookie_servlet/login.do">
<table border="1">
		<tr>
			<td>아이디</td>
			<td><input name="userid" id="userid">
				<input type="checkbox" id="chkSave">아이디 저장
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="passwd"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="로그인" id="btnLogin">
			</td>
		</tr>
</table>
</form>
</body>
</html>