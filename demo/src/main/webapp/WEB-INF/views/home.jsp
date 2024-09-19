<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="styleSheet" href="resources/myLib/myStyle.css">
<title>** Home SpringBoot **</title>
</head>
<style>
body {
	text-align: center;
}
</style>
<body>
	<h3>
		<span style="box-shadow: inset 0 -40px 0 White;">⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎
			ﾟ｡♡⋆｡⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎ ﾟ｡♡⋆｡‧</span> <br>
		<span style="box-shadow: inset 0 -40px 0 White;">💐🌿
			SpringBoot3 JPA 🌈🎄</span><br> <span
			style="box-shadow: inset 0 -40px 0 White;">⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎
			ﾟ｡♡⋆｡⋆｡♡ﾟ☁︎ ⋆｡ ﾟ☁︎ ﾟ｡♡⋆｡‧</span>
	</h3>
	<p>*Home_Time(server Time) : ${requestScope.serverTime }</p>
	<hr>
	<c:if test="${!empty requestScope.message }">
		<h4>${ requestScope.message}</h4>
	</c:if>
	<c:if test="${not empty sessionScope.loginName}">
	${ sessionScope.loginName} 님 안녕하세요! 
	</c:if>
	<br />
	<c:if test="${not empty sessionScope.loginInfo }">
	${ sessionScope.loginInfo}
	</c:if>
	<hr>
	<!-- 경로 : webapp은 생략 ! resources 부터넣기  -->
	<div>
		<img alt="mainImage" src="/resources/images/jjang9.gif" width="300"
			height="200" />
	</div>
	<hr>
	<c:if test="${not empty sessionScope.loginName }">
	&nbsp; <a href="member/memberDetail?jCode=D">MyInfo</a>&nbsp;	
&nbsp; <a href="member/memberDetail?jCode=U">내 정보수정</a>&nbsp;	
&nbsp; <a href="member/logout">Logout</a>&nbsp;	
&nbsp; <a href="member/delete">탈퇴</a>&nbsp;	
	</c:if>
	<c:if test="${empty sessionScope.loginName }">
	&nbsp;<a href="member/loginForm">LoginF</a>&nbsp;
	&nbsp;<a href="member/joinForm">JoinF</a>&nbsp;
	</c:if> &nbsp;
	<a href="member/memberList">Mlist</a>&nbsp; &nbsp;
	<a href="jo/joList">JoList</a>&nbsp; &nbsp;
	<a href="member/joindsl">JoinDSL</a>&nbsp; &nbsp;
	<br> &nbsp;
	<!-- GuessBook -->
	<a href="/ginsert">GInsert</a>&nbsp; &nbsp;
	<a href="/glist">GList</a>&nbsp; &nbsp;
	<a href="/gupdate">GUpdate</a>&nbsp; &nbsp;
	<a href="/gpage?pageNo=2">GPage</a>&nbsp;
	<br> &nbsp;
	<a href="/tsave">TSave</a>&nbsp; &nbsp;
	<a href="/tupdate">TUpdate</a>&nbsp; &nbsp;
	<a href="/tdupupdate">TDupUpdate</a>&nbsp; &nbsp;
	<a href="/tcalc">TCalc</a>&nbsp; &nbsp;
	<a href="/tlist">TList</a>&nbsp;
</body>
</html>