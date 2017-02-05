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
$(document).ready(function(){
	$("#btnWrite").click(function(){
		location.href="${path}/board/write.jsp";
	});
});
function list(page){
	location.href="${path}/board_servlet/search.do?curPage="+page+"&search_option=${search_option}&keyword=${keyword}";
}
</script>
</head>
<body>
<h2>게시판</h2>
<form name="form1" method="post" action="${path}/board_servlet/search.do">
	<select name="search_option">
		<option value="all" <c:if test="${search_option == 'all'}">selected</c:if>>전체검색</option>
		<option value="writer" <c:if test="${search_option == 'writer'}">selected</c:if>>이름</option>
		<option value="subject" <c:if test="${search_option == 'subject'}">selected</c:if>>제목</option>
		<option value="content" <c:if test="${search_option == 'content'}">selected</c:if>>내용</option>
<%-- 		<c:choose>
			<c:when test="${search_option == 'all'}">
				<option value="all" selected>전체검색</option>
				<option value="writer">이름</option>
				<option value="subject">제목</option>
				<option value="content">내용</option>
			</c:when>
			<c:when test="${search_option == 'writer'}">
				<option value="all" >전체검색</option>
				<option value="writer" selected>이름</option>
				<option value="subject">제목</option>
				<option value="content">내용</option>
			</c:when>
		</c:choose> --%>
	</select>
	<input name="keyword" value="${keyword}">
	<input type="submit" value="검색" id="btnSearch">
	<button type="button" id="btnWrite">글쓰기</button>
</form>
<table border="1" width="800px">
	<tr>
		<th>번호</th>
		<th>이름</th>
		<th>제목</th>
		<th>날짜</th>
		<th>조회수</th>
		<th>첨부파일</th>
		<th>다운로드</th>
	</tr>
<c:forEach var="dto" items="${list}">
	<tr>
		<td>${dto.num}</td>
		<td>${dto.writer}</td>
		<td>
			<!--답변 들여쓰기  -->
			<c:forEach var="i" begin="1" end="${dto.re_level}">
				&nbsp;&nbsp;
			</c:forEach>
			<a href="${path}/board_servlet/view.do?num=${dto.num}">${dto.subject}</a>
			<c:if test="${dto.comment_count > 0 }">
				<span style="color:green;">(${dto.comment_count})</span>
			</c:if>
		</td>
		<td>${dto.reg_date}</td>
		<td>${dto.readcount}</td>
		<td align ="center">
			<c:if test="${dto.filesize > 0}">
				<a href="${path}/board_servlet/download.do?num=${dto.num}">
					<img src="${path}/images/${dto.ext}.gif">
				</a>
			</c:if>
		</td>
		<td>${dto.down}</td>
	</tr>
</c:forEach>
<!--페이지 네비게이션 출력 영역  -->
	<tr>
		<td colspan="7">
				<!--처음  -->
			<c:if test="${page.curBlock  > 1}">
				<a href="javascript:list('1')">[처음]</a>
			</c:if>
			<!--이전  -->
			<c:if test="${page.curBlock  > 1}">
				<a href="javascript:list('${page.prevPage}')">[이전]</a>
			</c:if>
			<c:forEach var="num" begin="${page.blockStart}" end="${page.blockEnd}">
				<c:choose>
					<c:when test="${num == page.curPage}">
						<span style="color:red">${num}</span>
					</c:when>
					<c:otherwise>
						<a href="javascript:list('${num}')">${num}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!--다음  -->
			<c:if test="${page.curBlock  <= page.totBlock}">
				<a href="javascript:list('${page.nextPage}')">[다음]</a>
			</c:if>
			<!--끝  -->
			<c:if test="${page.curBlock  < page.totPage}">
				<a href="javascript:list('${page.totPage}')">[끝]</a>
			</c:if>
		</td>
	</tr>
</table>
</body>
</html>