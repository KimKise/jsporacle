<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration" %>
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
<%	//Enumeration<String> 집합을처리, session.getAttributeNames(); 세션변수 전체를 가져옴
	Enumeration<String> attr = session.getAttributeNames();
	while(attr.hasMoreElements()){ //다음 요소가 있으면
		//다음 요소
		String attrName = attr.nextElement();
		//session.getAttribute("세션변수명") 세션값 조회
		Object attrValue = session.getAttribute(attrName);
		out.println("세션의 속성명: "+attrName);
		out.println("세션의 속성값: "+attrValue+"<br>");
	}
	//session 하나씩 불러올 때, getAttribute는 Object타입으로 받아오기 때문에 형변환이 필요
	String id = (String)session.getAttribute("id");
	int age=(Integer)session.getAttribute("age");
	
	//session 수정
	//session.setAttribute("id", "park");	//수정 메소드가 별도로 없음, set을 다시 함
%>
<!--  EL 태그로 받아오기  -->
아이디 : ${sessionScope.id}<br>
비번 : ${sessionScope.passwd}<br>
나이 : ${sessionScope.age}<br>

<a href="deleteSession.jsp">세션 삭제</a>
</body>
</html>