<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
</head>
<body>
	<h2>💜 Board Update 💜</h2>
	<form action="update" method="Post">
		<table>
			<tr height="40">
				<th bgcolor="Linen">Seq</th>
				<td><input type="text" name="seq" value="${apple.seq}" style="border: 0px"
					size="20" readonly></td>
			</tr>
			<tr height="40">
				<th bgcolor="Linen">Title</th>
				<td><input type="text" name="title" value="${apple.title}"
					size="20"></td>
			</tr>
			<tr height="40">
				<th bgcolor="Linen">ID</th>
				<td><input type="text" name="id" value="${apple.id}"
					size="20" readonly style="border: 0px"></td>
			</tr>
			<tr height="40">
				<th bgcolor="Linen">Content</th>
				<td><textarea rows="5" cols="30" name="content"> ${apple.content}</textarea></td>
			</tr>
			<tr height="40">
				<th bgcolor="Linen">Regdate</th>
				<td><input type="text" name="regdate" value="${apple.regdate}"
					size="20" readonly style="border: 0px"></td>
			</tr>
			<tr height="40">
				<th bgcolor="Linen">Cnt</th>
				<td><input type="text" name="cnt" value="${apple.cnt}"
					size="20" readonly style="border: 0px"></td>
			</tr>

			<tr>
				<th></th>
				<td><input type="submit" value="수정">&nbsp;&nbsp; <input
					type="reset" value="취소"></td>
			</tr>
		</table>
	</form>

	<c:if test="${empty requestScope.apple}">
		<hr>
	~~ 출력할 자료가 없습니다. ~~<br>
	</c:if>

	<hr>
	&nbsp;
	<a href="boardList">[BoardList]</a> &nbsp;
	<a href="javascript:history.go(-1)">이전으로</a>&nbsp; &nbsp;
	<a href="/home">[Home]</a>
</body>
</html>