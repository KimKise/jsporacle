<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"  %>
<%@ page import="common.Util" %>
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
<!--  쿠키이용한 방문횟수   -->
<%						/*request.getCookies() 현재 사이트에서 사용하는 쿠키 배열
							count라는 쿠키는 처음에 null */
	String count = Util.getCookie(request.getCookies(), "count");
	//최근 방문 시간을 쿠키에 저장
	Date date = new Date();
	long now_time = date.getTime(); //현재 시간(초기준으로 리턴)
	String visitTime = Util.getCookie(request.getCookies(), "visit_time"); //쿠키 생성 visit_time이라는
	long visit_time=0;
	//쿠키가 존재하면
	if(visitTime != null && !visitTime.equals("")){
		visit_time = Long.parseLong(visitTime);	//스트링을 다시 롱타입으로 변환
	}
	//방문 횟수
	if(count==null || count.equals("")){	//처음 방문하면 쿠키 신규 생성 
		response.addCookie(new Cookie("count", "1"));//count라는 이름으로 쿠키를 셋팅
		//방문시간을 쿠키에 저장
	 	response.addCookie(new Cookie("visit_time", Long.toString(now_time)));
		out.println("첫방문을 환영합니다.");
	}else{
		//최근 방문시간과 현재 시간을 비교 
		long period = now_time - visit_time;
		out.println("현재: "+now_time+"<br>");
		out.println("최근 방문 시간: "+visit_time+"<br>");
		out.println("시차: "+period+"<br>"); //이것이 5초가 지나면 올라감
		int intCount = Integer.parseInt(count)+1;
		if(period >5*1000){//5초 이후면
			//카운트 증가
			response.addCookie(new Cookie("count", Integer.toString(intCount)));//저장 할때는 다시 스트링으로 바꿔줘야함
			//방문시간 업데이트
			response.addCookie(new Cookie("visit_time",Long.toString(now_time)));
		}
		out.println("방문횟수:"+intCount);
		//이미지
		//문자열.charAt(인덱스) => 문자열에서 몇번 째 글자를 뽑아옴, 
		//	547 : charAt(0) => 5, charAt(1) => 4
		String counter = Integer.toString(intCount);
		for(int i=0; i<counter.length(); i++){
			String img = "<img src='../images/"+counter.charAt(i)+".gif'>";
			out.println(img);
		}
	}
	
%>
</body>
</html>