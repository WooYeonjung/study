<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** SpringBoot3 JPA LoginForm **</title>
</head>
<body>
<h2>** SpringBoot3 JPA LoginForm **</h2>
<form action="login" method="post"> <!-- /member 하위에서 실행되는것이기때문에 경로상에 login만 기재해도 됨.  -->
<table>
	<tr height="40"><td bgcolor="mistyrose"><label for="id">ID</label></td>
		<td><input type="text" id="id" name="id"></td>
	</tr>
	<tr height="40"><td bgcolor="mistyrose"><label for="password">Password</label></td>
		<td><input type="password" id="password" name="password"></td>
	</tr>
	<tr height="40"><td></td>
		<td><input type="submit" value="로그인">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td>
	</tr>
</table>
</form>
<hr>
 <%--<%	if ( request.getAttribute("message") !=null ) {
	// message 출력
	=> <%=request.getAttribute("message")%>
<%	}%> --%>

<a href="/home">Home</a>&nbsp; &nbsp;
	<a href="javascript:history.go(-1)">이전으로</a>&nbsp;

</body>
</html>