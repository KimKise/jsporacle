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
<!-- 
	cookie.쿠키변수명.value => 쿠키변수의 값  
	JAVA코드로 쿠키 출력 
	여기서 JSESSIONID 1F5B55AB48371B7231107160ED192814는 클라이언트의 식별자 이다. 
	웹브라우저가 가르거나 닫았가가 다시 열면 다시 부여 / 새탭, 새창으로 열면 같음
-->
<%
	Cookie[] cookies = request.getCookies(); //쿠키배열타입으로 되어있음, 쿠키를 받아롬 다른 페이지에서
	if(cookies != null){				
		for(int i=0; i<cookies.length; i++){	
			
				out.println("쿠키이름:"+cookies[i].getName());
				out.println("쿠키값:"+cookies[i].getValue()+"<br>");
		}
	}

%>
<!-- 
	cookie.쿠키변수명.value => 쿠키변수의 값  
	EL태그로 쿠키 출력 
-->
	아이디 : ${cookie.id.value}<br>
	비번 : ${cookie.pwd.value}<br>
	<a href="deleteCookie.jsp">쿠키 삭제</a>
	<a href="editCookie.jsp">쿠키 변경</a>
</body>
</html>