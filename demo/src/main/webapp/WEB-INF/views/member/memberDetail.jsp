<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>**MVC2 Member Detail**</title>
</head>
<body>
	<h2>**MVC2 Member Detail**</h2>
	<table style="width: 100%">
		<c:if test="${not empty requestScope.detail}">
			<tr height="40">
				<th bgcolor="seashell">Id</th>
				<td>${requestScope.detail.id}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Password</th>
				<td>${requestScope.detail.password}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Name</th>
				<td>${requestScope.detail.name}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Age</th>
				<td>${requestScope.detail.age}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Info</th>
				<td>${requestScope.detail.info}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Point</th>
				<td>${requestScope.detail.point}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Birthday</th>
				<td>${requestScope.detail.birthday}</td>
			</tr>
			<tr height="40">
				<th bgcolor="seashell">Recommend</th>
				<td>${requestScope.detail.rid}</td>
			</tr>
						<tr height="40">
				<th bgcolor="seashell">Image</th>
				<td><img alt="${requestScope.detail.uploadfile}" width="50" height="70"
					src="/resources/uploadImages/${requestScope.detail.uploadfile}"></td>
			</tr>

		</c:if>
		<c:if test="${empty requestScope.detail}">
			<tr>
				<td colspan="9">출력 할 자료가 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<hr>
&nbsp;<a href="/home">Home</a>&nbsp;
</body>
</html>