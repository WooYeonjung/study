<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="styleSheet" href="/resources/myLib/myStyle.css">
<title>Insert title here</title>
</head>
<body>
	<h2>🐻 board test🐻</h2>
	<hr
		style="border-width: 1px 0 0 0; border-style: dashed; border-color: deepskyblue; width: 700px;">
	<c:if test="${!empty requestScope.message}">${requsetScope.message}
    <hr
			style="border-width: 1px 0 0 0; border-style: dashed; border-color: deepskyblue; width: 700px;">
	</c:if>
	<table style="width: 100%">
		<tr bgcolor="khaki">
			<th>Seq</th>
			<th>Title</th>
			<th>ID</th>
			<th>RegDate</th>
			<th>Cnt</th>
		</tr>

		<c:if test="${!empty requestScope.banana}">
			<c:forEach var="b" items="${requestScope.banana}">
				<tr>
					<td>${b.seq}</td>




					<td>
						<!-- 답글 등록 후 Title 출력 전에 들여쓰기 추가 --> 
						<c:if test="${b.indent > 0}">
							<c:forEach begin="1" end="${b.indent}">
								<span>&nbsp;&nbsp;</span>
							</c:forEach>
							<span style="color: palevioletred;"><b>re:</span>
						</c:if> 
						
						<!-- login 한 경우에만 detail 실행 할 수 있도록 --> 
						<c:if test="${!empty sessionScope.loginID}">
							<a href="boardDetail?jCode=D&seq=${b.seq}">${b.title}</a>
						</c:if> 
						<c:if test="${empty sessionScope.loginID}">
							${b.title}
						</c:if>
					</td>
					<td>${b.id}</td>
					<td>${b.regdate}</td>
					<td>${b.cnt}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty requestScope.banana}">
			<tr>
				<td colspan="5">출력할 자료가 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<br>
	<c:if test="${!empty sessionScope.loginID}">
	&nbsp;<a href="boardInsert">새글등록</a>
	</c:if>
	&nbsp;
	<a href="javascript:history.go(-1)">이전으로</a>&nbsp; &nbsp;
	<a href="/home">[Home]</a>
</body>
</html>