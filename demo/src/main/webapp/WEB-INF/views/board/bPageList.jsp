<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Spring_Boot Mybatis BoardPageList **</title>
<link rel="stylesheet" type="text/css" 
	  href="/resources/myLib/myStyle.css">
<script>
"use strict"

//1. keywordClear 
// searchType 을 all 로 변경했을때 keyword는 Clear
function keywordClear(){
	if(document.getElementById("searchType").value =='all')
		document.getElementById("keyword").value='';
}//keywordClear

/* 
** self.location    
 1) location 객체 직접사용 Test : url로 이동, 히스토리에 기록됨(앞으로가기 뒤로가기)
 => self.location="bcrilist?currPage=?????" : 해당 요청을 서버로 전달 

 2) location 객체의 메서드
 => href, replace('...'), reload()  */

//2. searchDB 
// => 검색조건을 입력 후 버튼 클릭하면 
//    입력값들을 서버로 전송하는 요청 처리 : location 이용
function searchDB(){
	// 첫버째 페이지에서 검색을 누르면 null일 들어갈 수 있기때문에 makerPage 사용 할 수 없음.
	 self.location='bPageList'
	 				+'?currPage=1&rowsPerPage=5'
	 				+'&searchType='+document.getElementById("searchType").value
	 				+'&keyword='+document.getElementById("keyword").value;
}
 function checkEnter(event) {
     if (event.key === 'Enter') {
         searchDB();
     }
 }//checkEnter(event) 
 /* +'${pageMaker.makeQuery(1)}'
 => 하나의 jsp 문서로 다양한 요청을 처리하기위해 쿼리스트링에 mappingName 을 포함했고,
    기본요청 값(searchType, keyword) 들이 없기 때문에
    첫 요청에서는 makeQuery 메서드 사용할수 없음 */
/* 
 ** JS 코드 내부에서 el Tag 사용시 주의사항
  => JS 코드의 스트링 내에서 사용한 el Tag 는 JSP 가 처리해주므로   
  사용가능 하지만, 이 스크립트가 외부 문서인 경우에는 처리해주지 않으므로 주의
 이 코드를 외부문서로 작성하면 "${pageMaker.makeQuery(1)}" 이 글자 그대로 적용되어 404 발생 
 */  
 
//---------------------------------------------------------

//** querySelector
//=> css 선택자를 이용하여 첫번째 만난 요소 1개만 선택

//** querySelectorAll 
//=> css 선택자를 이용하여 해당하는 nodeList 를 반환
//=>  ","를 사용하면 여러 요소를 한번에 가져올 수 있음
//   querySelectorAll("#id,.class");
//=> 그러므로 반복문과 이용됨.

//** Board Check_List Test
//   취소버튼 클릭시에는 checked 값이 clear 되도록 function checkClear() 추가함 
//( reset 버튼은 기본적으로 새로고침과 동일하게 처리되어 
//  ${pageMaker.cri.check} 로 전달된 초기값이 계속 적용되기때문 )  
 
 function checkClear(){
	 //document.querySelectorAll(".clear"); xxxx //clear 클래스 모두 선택
	 // => 배열 형식으로 nodeList 를 반환하기 때문에 반복문으로 처리해야함
	 let ck=document.querySelectorAll(".clear")
	 for(let i = 0; i<ck.length(); i++){
		 ck[i].checked=false;
	 }
	 return false; 
	 // => reset 기능의 원래기능을 없애주기 위해!
	// <input type="reset" value="Clear" onclick="return checkClear()"> return false
 }
 
</script>
</head>
<body>
<h2>** Spring_Boot Mybatis BoardPageList **</h2>
<hr>
<c:if test="${!empty requestScope.message}">
 ${requestScope.message}<br><hr>
</c:if>
<hr>
<div id="searchBar">
	<select name="searchType" id="searchType" onChange="keywordClear()">
		<option value="all" ${pageMaker.cri.searchType=='all'?'selected':'' }>전체</option>
		<option value="title"  ${pageMaker.cri.searchType=='title'?'selected':'' }>제목</option>
		<option value="content"  ${pageMaker.cri.searchType=='content'?'selected':'' }>내용</option>
		<option value="id"  ${pageMaker.cri.searchType=='id'?'selected':'' }>글쓴이</option>
		<option value="regdate"  ${pageMaker.cri.searchType=='regdate'?'selected':'' }>작성일</option>
		<option value="tc"  ${pageMaker.cri.searchType=='tc'?'selected':'' }>제목+내용</option>
	</select>
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}" onkeydown="checkEnter(event)">
	<!-- PageMaker 가 Criteria를 가지고 있기때문에 가지고 올 수 있음. -->
	<button id="searchBtn" onclick="searchDB()"><b>🔎</b></button>
	<hr>
	
	<!-- CheckBox Test -->
	<form action="bCheckList" method="get">
		<b>ID: </b>
		<!-- check 의 선택한 값을 페이지가 이동해도 유지하기 위한 확인코드 -->
		<c:set var="ck1" value="false"/>
		<c:set var="ck2" value="false"/>
		<c:set var="ck3" value="false"/>
		<c:set var="ck4" value="false"/>
		<c:set var="ck5" value="false"/>
		<c:forEach var="id" items="${pageMake.cri.check}">
			<c:if test="${id=='admin' }"> <c:set var="ck1" value="true"/></c:if>		
			<c:if test="${id=='apple' }"> <c:set var="ck2" value="true"/></c:if>		
			<c:if test="${id=='banana' }"> <c:set var="ck3" value="true"/></c:if>		
			<c:if test="${id=='wyeonj' }"> <c:set var="ck4" value="true"/></c:if>		
			<c:if test="${id=='user1' }"> <c:set var="ck5" value="true"/></c:if>		
		</c:forEach>
		<!-- ----------------------------------------------------------------- -->
		
	 <input type="checkbox" name="check" id="admin" class="clear" value="admin" ${ck1 ? 'checkde': ''}>
        <label for="admin">관리자 🐻</label> &nbsp;
        <input type="checkbox" name="check" id="apple" class="clear" value="apple" ${ck2 ? 'checkde': ''}>
        <label for="apple">Apple🍎</label> &nbsp;
        <input type="checkbox" name="check" id="banana" class="clear" value="banana" ${ck3 ? 'checkde': ''} >
        <label for="banana">Banana🍌</label> &nbsp;
        <input type="checkbox" name="check" id="wyeonj" class="clear" value="wyeonj" ${ck4 ? 'checkde': ''}>
        <label for="wyeonj">wyeonj🍏</label> &nbsp;
        <input type="checkbox" name="check" id="user1" class="clear" value="user1" ${ck5 ? 'checkde': ''}>
        <label for="user1">user1🍓</label> &nbsp;&nbsp;
        <input type="submit" value="search" >&nbsp;
        <input type="reset" value="clear" onclick="return checkClear()"><br>
	</form>
	
</div>
<table style="width:100%">
<tr bgcolor="#7ba5f0" >
	<th>Seq</th><th>Title</th><th>ID</th><th>RegDate</th><th>조회수</th>
</tr>
<c:if test="${!empty requestScope.banana}">
	<c:forEach var="b" items="${requestScope.banana}">
		<tr><td>${b.seq}</td>
			<td>
			<!-- 답글 등록 후 Title 출력전에 들여쓰기 추가 -->
			<c:if test="${b.indent>0}">
				<c:forEach begin="1" end="${b.indent}">
					<span>&nbsp;&nbsp;</span>
				</c:forEach>
				<span style="color:Chocolate;"><b>re..</b></span>
			</c:if>
			
			<!-- 로그인 한 경우에만 글내용 볼 수 있도록 -->  
			<c:if test="${!empty loginID}">
				<a href="boardDetail?jCode=D&seq=${b.seq}">${b.title}</a>
			</c:if>
			<c:if test="${empty loginID}">
				${b.title}
			</c:if>
			</td>
			<td>${b.id}</td><td>${b.regdate}</td><td>${b.cnt}</td>
		</tr>	
	</c:forEach>
</c:if>
<c:if test="${empty requestScope.banana}">
	<tr><td colspan="5">~~ 출력자료가 1건도 없습니다. ~~ </td>
	</tr>
</c:if>
</table>
<hr>
<div align="center">
<!-- ** Paging Block ** 
	=> ver01: QueryString 수동 입력 -> 자동생성 makeQuery 메서드 적용
	=> ver02: makeQuery 메서드 -> searchQuery 메서드로 변경
 	1) FirstPage, Prev  
 	=> OLD
 		<a href="bPageList?currPage=1&rowsPerPage=5">FP</a>&nbsp;
		<a href="bPageList?currPage=${pageMaker.startPageNo-1}&rowsPerPage=5">&LT;</a>&nbsp;&nbsp; 
 	 
 	=> New : makeQuery 활용
 	 -->
  <c:choose>
	<c:when test="${pageMaker.prev && pageMaker.startPageNo>1}">
	 	<a href="${pageMaker.makeQuery(1)}">FP</a>&nbsp;
		<a href="${pageMaker.makeQuery(pageMaker.startPageNo-1)}">&LT;</a>&nbsp;&nbsp;  
	</c:when>	
	<c:otherwise>
		<font color="Gray">FP&nbsp;&LT;&nbsp;&nbsp;</font><!-- aTag 없이 흐리게 나오라고 설정 -->
	</c:otherwise>
  </c:choose> 	 
<!-- 2) Display PageNo 
	=> currPage 제외한 PageNo 만 a Tag 적용 -->
  <c:forEach var="i" begin="${pageMaker.startPageNo}" end="${pageMaker.endPageNo}">
  	<c:if test="${i==pageMaker.cri.currPage}">
  		<font color="Orange" size="5"><b>${i}</b></font>&nbsp;
  	</c:if>
  	<c:if test="${i!=pageMaker.cri.currPage}">
  		<a href="${pageMaker.makeQuery(i)}">${i}</a>&nbsp;
  	</c:if>
  </c:forEach>
<!-- 3) Next, LastPage  -->
  <c:choose>
  	<c:when test="${pageMaker.next && pageMaker.endPageNo>0}">
  		&nbsp;<a href="${pageMaker.makeQuery(pageMaker.endPageNo+1)}">&GT;</a>
  		&nbsp;<a href="${pageMaker.makeQuery(pageMaker.lastPageNo)}">LP</a>
  	</c:when>
  	<c:otherwise>
  		<font color="Gray">&nbsp;&GT;&nbsp;LP</font>
  	</c:otherwise>
  </c:choose>
</div>
<hr>
<!-- 로그인 한 경우에만 새글등록 할 수 있도록 -->  
<c:if test="${not empty sessionScope.loginID}">
	&nbsp;<a href="boardInsert">새글등록</a>&nbsp;
</c:if>
&nbsp;<a href="/home">Home</a>&nbsp;
&nbsp;<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
</body>
</html>