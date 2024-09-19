<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/resources/myLib/myStyle.css">
<title>Board Detail</title>
</head>
<body>
	<h2>💜 Board Detail 💜</h2>
	<c:if test="${not empty requestScope.apple}">
		<table>
			<tr height="40">
				<td bgcolor="Lavender">Seq</td>
				<td>${apple.seq}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Lavender">Title</td>
				<td>${apple.title}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Lavender">ID</td>
				<td>${apple.id}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Lavender">Content</td>
				<td>${apple.content}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Lavender">Regdate</td>
				<td>${apple.regdate}</td>
			</tr>
			<tr height="40">
				<td bgcolor="Lavender">조회수</td>
				<td>${apple.cnt}</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${empty requestScope.apple}">
		<hr>
	~~ 출력할 자료가 없습니다. ~~<br>
	</c:if>
	<c:if test="${!empty requestScope.message}">
	 <hr> ${requestScope.message}
	</c:if>
	<hr>
	<!-- 로그인 한 경우에는 새글등록, 답글 작성
	=> 답글 : 부모글의 root,step,indent 값이 필요하기 때문에  
	-->
	<c:if test="${!empty sessionScope.loginID}">
	&nbsp;<a href="boardInsert">새글등록</a>&nbsp;
	&nbsp;<a href="replyInsert?root=${apple.root}&step=${apple.step}&indent=${apple.indent}">답글등록</a>&nbsp;
	<!-- form 을 띄어주기 위함. -->
	</c:if>
	<c:if
		test="${!empty sessionScope.loginID and sessionScope.loginID==apple.id}">
		<a href="boardDetail?jCode=U&seq=${apple.seq}">글수정</a> &nbsp;
		<a href="delete?seq=${apple.seq}&root=${apple.root}">글삭제</a>
	</c:if>
	<hr>
	&nbsp;
	<a href="boardList">[BoardList]</a> &nbsp;
	<a href="javascript:history.go(-1)">이전으로</a>&nbsp; &nbsp;
	<a href="/home">[Home]</a>
</body>
</html>