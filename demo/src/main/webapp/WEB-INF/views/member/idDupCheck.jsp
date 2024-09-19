<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** ID 중복 확인 **</title>
<link rel="stylesheet" type="text/css"
	href="/resources/myLib/myStyle.css">
<script type="text/javascript" src="/resources/myLib/inCheck.js"></script>
<script type="text/javascript">


//** idOK : 사용자가 입력한 id를 사용가능하도록 해주고, 현재(this)창은 close
//1) this 창의 id 를 부모창의 id 로
//2) 부모창의 ID중복확인 버튼은 disable & submit 은 enable
//3) 부모창의 id 는 수정불가 (readonly) , password 에 focus
//4) 현재(this)창은 close
 function idOK(){
	//1) this 창의 id 부모창의 id 로 
	opener.document.getElementById("id").value ="${param.id}";
										//=> document.getElementById("id").value;
										//=> EL 활용 : JSP 문서 내부의 script 구문의 문자열 내부의 EL은 처리해 줌 
										//=> "${param.id}" 에서 ""가 없다면 jqeury로 인식됨. 
	//2) submit 은 enable
	opener.document.getElementById("submitTag").disabled='';
	
	//3)  부모창의 id 는 수정불가 (readonly) , password 에 focus
	// => readonly 속성 사용시 주의
    //    Tag 의 속성은 readonly로 정의되어 있지만, ( readonly="readonly" )
    //    DOM 의 node 객체에서는 readOnly 로 정의되어있으므로
    //    JS 코딩시에는 readOnly 로 사용해야함
    //opener.document.getElementById('id').readonly="readonly"; //XXX
    //opener.document.getElementById('id').readOnly="readOnly"; //OK
	opener.document.getElementById("id").readOnly=true;
	opener.document.getElementById("password").focus();
	
	// 4) 현재(this)창은 close
	close();
	//=> window.close() , self.close() 모두 가능
}


</script>
<style>
	body{
		background-color : lightYellow;
		font-family: 맑은고딕;
	}
	#wrap{
		margin-left:0;
	}
</style>
</head>
<body>
<div id="wrap">
	<h3>** ID 중복 확인 **  </h3>
	<form action="idDupCheck" method="get">
		User_ID:
		<input type="text" name ="id" id="id" value="${param.id}">
		<input type="submit" value="ID 중복확인" onclick="return idCheck()">
		<br><span id="iMessage" class="eMessage"></span>
	</form>
	<br>
	<!-- 서버처리 결과 : idUse 의 값 T/F 에 따른 Message 출력 -->
	<div id="msgBlock">
		<c:if test="${idUse=='T'}">
			${param.id} 는(은) 사용가능 합니다.  &nbsp;&nbsp; 
			<button onclick="idOK()">ID 선택</button><!-- form 내부가 아니기때문에 그냥 button -->
		</c:if>
		<c:if test="${idUse=='F'}">
			${param.id } 는(은) 이미 사용중입니다.  &nbsp;&nbsp; <br>
			다시 입력하세요 ~<br>
			<!-- 부모창에 남아있는 id 삭제, id 중복확인 버튼 disable
				현재창의 id창에는 focus 지정해서 재입력 유도 ->script 필요 
				 -->
			<script>
				window.document.getElementById("id").focus();
				opener.document.getElementById("id").value='';
				opener.document.getElementById("idDup").disabled='disabled';
			</script>
		</c:if>
	</div>
</div>
</body>
</html>