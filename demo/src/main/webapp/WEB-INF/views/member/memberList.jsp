<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring MemberList **</title>
<style>

</style>
</head>
<body>
	<h2>** Web MVC2 MemberList **</h2>
	<table border="1" style="width: 100%">
		<tr bgcolor="honeyDew">
			<th>Id</th>
			<th>Password</th>
			<th>Name</th>
			<th>Age</th>
			<th>Jno</th>
			<th>Info</th>
			<th>Point</th>
			<th>Birthday</th>
			<th>Rid</th>
			<th>Image</th>
		</tr>
		<c:if test="${not empty requestScope.slist}">
			<c:forEach var="m" items="${requestScope.slist}">
				<tr>
					<td>${m.id}</td>
					<!-- getter를 가지고옴. s.getSno()를 가지고옴. -->
					<td>${m.password}</td>
					<td>${m.name}</td>
					<td>${m.age }</td>
					<td>${m.jno }</td>
					<td>${m.info}</td>
					<td>${m.point}</td>
					<td>${m.birthday}</td>
					<td>${m.rid}</td>
					<td><img alt="${m.uploadfile}" width="50" height="70"
					src="/resources/uploadImages/${m.uploadfile}"></td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty requestScope.slist}">
			<tr>
				<td colspan="9">출력 할 자료가 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<hr>
	&nbsp;
	<a href="/home">Home</a>&nbsp; &nbsp;
	<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
</body>
</html>