<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring Axios MemberList **</title>
<link rel="stylesheet" type="text/css" 
	  href="/resources/myLib/myStyle.css"/>
</head>
<!-- 
** 검색 & 페이징 포함한 요청의 Ajax 처리
=> Ajax 요청 function 작성, url 을 매개변수로 전달 : axiMListCri(url) 
=> Page 요청 : aTag -> span 으로 변경하고 function 으로 처리 
=> Check 검색은 submit 을 사용하기 때문에 적용하지 않음(주석처리)

=> Ajax 처리시에는 문서내부의 function이 인식 안되므로
   searchDB(), keywordClear(), checkClear() 모두 axTest03.js 에 작성   
-->
<body>
	<h2>** Spring MemberList **</h2>
	<hr>
	<div id="searchBar">
	<select name="searchType" id="searchType" onChange="keywordClear()">
		<option value="all" ${pageMaker.cri.searchType=='all'?'selected':'' }>전체</option>
		<option value="id"  ${pageMaker.cri.searchType=='id'?'selected':'' }>ID</option>
		<option value="name"  ${pageMaker.cri.searchType=='name'?'selected':'' }>Name</option>
		<option value="age"  ${pageMaker.cri.searchType=='id'?'age':'' }>Age</option>
		<option value="birthday"  ${pageMaker.cri.searchType=='birthday'?'selected':'' }>Birthday</option>
		<option value="info"  ${pageMaker.cri.searchType=='info'?'selected':'' }>Info</option>
	</select>
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}" onkeydown="checkEnter(event)">
	<!-- PageMaker 가 Criteria를 가지고 있기때문에 가지고 올 수 있음. -->
	<button id="searchBtn" onclick="searchDB()"><b>🔎</b></button>
	<hr>
	
	<!-- CheckBox Test -->
	<form action="mCheckList" method="get">
		<b>JNO: </b>
		<!-- check 의 선택한 값을 페이지가 이동해도 유지하기 위한 확인코드 -->
		<c:set var="ck1" value="false"/>
		<c:set var="ck2" value="false"/>
		<c:set var="ck3" value="false"/>
		<c:set var="ck4" value="false"/>
		<c:set var="ck5" value="false"/>
		<c:forEach var="jno" items="${pageMaker.cri.check}">
			<c:if test="${jno=='1' }"> <c:set var="ck1" value="true"/></c:if>		
			<c:if test="${jno=='2' }"> <c:set var="ck2" value="true"/></c:if>		
			<c:if test="${jno=='3' }"> <c:set var="ck3" value="true"/></c:if>		
			<c:if test="${jno=='4' }"> <c:set var="ck4" value="true"/></c:if>		
			<c:if test="${jno=='7' }"> <c:set var="ck5" value="true"/></c:if>		
		</c:forEach>
		<!-- ----------------------------------------------------------------- -->
	 <input type="checkbox" id="1" class="check clear" value="1" ${ck1 ? 'checked': ''}>
        <label for="1">우린팀 🐻</label> &nbsp;
        <input type="checkbox"   id="2"  class="check clear" value="2" ${ck2 ? 'checked': ''}>
        <label for="2">모꼬지🍎</label> &nbsp;
        <input type="checkbox" id="3"  class="check clear" value="3" ${ck3 ? 'checked': ''} >
        <label for="3">OoC🍌</label> &nbsp;
        <input type="checkbox"  id="4"  class="check clear" value="4" ${ck4 ? 'checked': ''}>
        <label for="4">컴포NaN트🍏</label> &nbsp;
        <input type="checkbox"  id="7"  class="check clear" value="7" ${ck5 ? 'checked': ''}>
        <label for="7">관리팀🍓</label> &nbsp;&nbsp;
       
       <!--Axios 적용
 		  => submit 을 Button(type 속성주의) 으로 변경  -->
        <button type="button" onclick="axiMListCheck()" >Check 검색</button>&nbsp;
        <input type="reset" value="clear" onclick="return checkClear()"><br>
	</form>
</div>
	<hr>
	<table border="1" style="width: 100%">
		<tr bgcolor="Pink">
			<th>Id</th>
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
				<td colspan="8">출력 할 자료가 없습니다.</td>
			</tr>
		</c:if>
	</table>
	<hr>
	<div align="center">
	<!-- 1) FirstPage, Prev -->
		<c:choose>
			<c:when test="${pageMaker.prev && pageMaker.startPageNo>1}">
			
		<span class="textlink" onclick="axiMListCri('${pageMaker.makeQuery(1)}') ">FP </span>&nbsp;
		<span  class="textlink" onclick="axiMListCri('${pageMaker.makeQuery(pageMaker.startPageNo-1)}')">&LT; </span>&nbsp;&nbsp;  

	</c:when>
			<c:otherwise>
				<font color="Gray">FP&nbsp;&LT;&nbsp;&nbsp;</font>
				<!-- aTag 없이 흐리게 나오라고 설정 -->
			</c:otherwise>
		</c:choose>
	<!-- 2) Display PageNo 
	=> currPage 제외한 PageNo 만 a Tag 적용 -->
		<c:forEach var="i" begin="${pageMaker.startPageNo}"
			end="${pageMaker.endPageNo}">
			<c:if test="${i==pageMaker.cri.currPage}">
				<font color="Orange" size="5"><b>${i}</b></font>&nbsp;
  	</c:if>
	<c:if test="${i!=pageMaker.cri.currPage}">
			<span class="textlink" onclick="axiMListCri('${pageMaker.makeQuery(i)}') ">${i} </span>&nbsp;
  	</c:if>
		</c:forEach>
		<!-- 3) Next, LastPage  -->
		<c:choose>
			<c:when test="${pageMaker.next && pageMaker.endPageNo>0}">
		&nbsp;<span class="textlink" onclick="axiMListCri('${pageMaker.makeQuery(pageMaker.endPageNo+1)}') ">&GT; </span>
 		&nbsp;<span class="textlink" onclick="axiMListCri('${pageMaker.makeQuery(pageMaker.lastPageNo)}') ">LP </span>
			</c:when>
			<c:otherwise>
				<font color="Gray">&nbsp;&GT;&nbsp;LP</font>
			</c:otherwise>
		</c:choose>
	</div>
	<hr>
	&nbsp;
	<a href="/home">Home</a>&nbsp; &nbsp;
	<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
</body>
</html>