// ** Ajax_REST API, Axios Test **
// => Axios 메서드형식 적용
// => 1. List 출력
//    - axiMList : MemberController, Page response (axmemberList.jsp)

// => 2. 반복문에 이벤트 적용하기
//    - idbList(id별 boardList) : RESTController, List_Data response 
//    - Delete, JoDetail
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

"use strict"
// 1. List 출력
// 1.1) Page Response
// => response를 resultArea1에 출력
// => 요청 url : /member/aximlist
// 	 response : axmemberList.jsp
function axiMList() {
	let url = "/member/aximlist";
	axios.get(url
	).then(response => {
		console.log(`** response 성공 `);
		document.getElementById("resultArea1").innerHTML = response.data;
	}).catch(err => {
		alert(` ** response 실패 => ${err.message}`)
	});
	document.getElementById("resultArea2").innerHTML = '';
}//axiMList

// => 2. 반복문에 이벤트 적용하기
// 2.1) idbList(id 별 boardList)
// => RESTController, PathVariable 처리,  List_Data response 
// => Server : service, Sql 구문 작성
// => request : id 를 path 로 전송 "/rest/idblist/banana"
// => Response 
//    -> 성공 : 반복분, Table로 List 출력문 완성, resultArea2 에 출력
//    -> 출력자료의 유/무 : Server 에서 status로 (없으면 502) 처리
//    -> 실패 : resultArea2 clear, alert 으로 에러메시지 출력 
function idbList(id) {
	let url = `/rest/idblist/${id}`; // 또는 "/rest/idblist/"+id;

	axios.get(url

	).then(response => {
		console.log(`** idbList 성공 => ${response.data}`);
		let listData = response.data;
		let resultHtml =
			`<table style="width: 100%">
			<tr bgcolor="khaki">
				<th>Seq</th>
				<th>Title</th>
				<th>ID</th>
				<th>RegDate</th>
				<th>Cnt</th>
			</tr>`
		// *** 반복문 적용
		// ** for 간편출력 : of, in
		// => in: undifined 는 통과하고, 배열(index Return), 객체(속성명 Return)
		// => of: undifined 까지 모두출력 (순차출력과 동일), value 를 return, 
		//          ES6 에 for ~ in 의 단점을 보완 개선하여 추가됨.
		//          일반 객체에는 적용안되지만, (오류발생, 개발자모드로 확인가능)
		//           Array, String, Map, Set, function의 매개변수 객체 와
		//          이터러블 규약을 따르는 이터러블 객체 (Iterable Object) 는 적용됨
		// => 이터러블 규약
		//        내부에 Symbol.iterator (줄여서 @@iterator로 표현하기도함) 메서드가 구현되어 있어야 한다는 규약 
		for (let b of listData) {
			resultHtml += `
				<tr>
					<td>${b.seq}</td>
					<td>${b.title}</td>
					<td>${b.id}</td>
					<td>${b.regdate}</td>
					<td>${b.cnt}</td>
				</tr>`;
		}
		resultHtml += `</table>`;

		document.getElementById("resultArea2").innerHTML = resultHtml;
	}).catch(err => {
		// => response의 status 값이 502 => "출력 자료 없음"
		if (err.response.status == '502') {

			document.getElementById("resultArea2").innerHTML
				= err.response.data;
		} else {
			document.getElementById("resultArea2").innerHTML
				= '** 시스템 오류입니다. 잠시 후 다시하세요. **' + err.message;
		}
	});
}//idbList


//2.2) axiDelete
//=> 요청명 : "/rest/axidelete/",PathVariable 적용
// => response: 성공/ 실패 여부만(그러므로 RestController 로 )
// => 성공 : Deleted 로 변경, onclick 이벤트 해제
function axiDelete(e, id) {
	let url = "/rest/axidelete/" + id;
/*	let left=e.pageX;
	let right=e.pageY;*/
	axios.delete(url
	).then(response => {
		alert(`** 삭제 성공 => ${response.data}`);
		//=> 삭제 성공
		// - Delete -> Deleted 로 변경, color_Gray, Bold
		// - onClick 이벤트 제거
		// - Style 제거
		/*document.getElementById(id).innerHTML="Delete";
		document.getElementById(id).style.color="gray";
		*/
		// => e.target wjrdyd
		e.target.innerHTML = "Deleted";
		e.target.style.color = 'gray';

		document.getElementById(id).style.fontWeight = "bold";
		document.getElementById(id).removeAttribute('onclick');
		document.getElementById(id).classList.remove('textlink');
	}).catch(err => {
		if (err.response.status == '502') {
			alert(`** 삭제 실패 => ${err.response.data}`)
		} else {
			alert(`** 시스템 오류, 잠시 후 다시하세요. => ${err.message}`)
		}

	});
}//axiDelete


function showJoDetail(e, jno) {
	let url = `/rest/jodetail/${jno}`;

	axios.get(url
	).then(response => {
		console.log(`** joLIst 성공 => ${JSON.stringify(response.data)}`);
		let listData = response.data;
		let resultHtml =
			`<table style="width: 100%">
			<tr height="10"><th>Jno</th><td>${listData.jno}</td></tr>
			<tr height="10"><th>Jname</th><td>${listData.jname}</td></tr>
			<tr height="10"><th>CaptainID</th><td>${listData.captain}</td></tr>
			<tr height="10"><th>Project</th><td>${listData.project}</td></tr>
			<tr height="10"><th>Slogan</th><td>${listData.slogan}</td></tr>
			</table>`;
		let content = document.getElementById("content");
		content.innerHTML = resultHtml;
		content.style.display='block';
		content.style.left = `${e.pageX+20}px`;
		content.style.top = `${e.pageY}px`;
	}).catch(err => {
		// => response의 status 값이 502 => "출력 자료 없음"
		if (err.response.status == '502') {
			document.getElementById("content").innerHTML
				= '** 조회 실패 **' + err.message;
		} else {
			document.getElementById("content").innerHTML
				= '** 시스템 오류입니다. 잠시 후 다시하세요. **' + err.message;
		}
	});
}//showJoDetail

function joHide(){
	document.getElementById("content").style.display='none';
	
}

// ** Ajax Member_PageList *********************
// => axiMList 에 Paging + 검색기능 추가
// => 검색조건 & Paging , Ajax 구현
//     -> 입력된 값들을 서버로 전송요청: axios
//    -> url 완성후 axios 호출

// => 3.1) 검색조건 입력 후 버튼클릭
//    -> jsp  문서내무의 script 구문을 외부문서로 작성 : EL Tag 적용안됨
//    ${pageMaker.makeQuery(1)} -> ?currPage=1&rowsPerPage=5 

function searchDB(){
	//요청 url을 작성
	 let url='axmcri'
	 				+'?currPage=1&rowsPerPage=5'
	 				+'&searchType='+document.getElementById("searchType").value
	 				+'&keyword='+document.getElementById("keyword").value;
	axiMListCri(url); //3.3) Axios 요청 처리
}

// => 3.2) keywordClear
// => seachType 을 all 선택시 keyword는 clear
function keywordClear(){
	if(document.getElementById("searchType").value =='all')
		document.getElementById("keyword").value='';
}//keywordClear

//---------------------------------------------------------------------------------

// => 3.3) Axios 요청 처리
// => 첫 요청 :/member/axmcri (axTestForm.jsp 의 메뉴 확인)
// => Parameter 는 쿼리 스트링으로
function axiMListCri(url){
	url ="/member/"+url;
	console.log(`** axiMListCri url=> ${url}`);
	
	axios.get(url
	).then(response =>{
		document.getElementById("resultArea1").innerHTML = response.data;
	}).catch(err => {
		document.getElementById("resultArea1").innerHTML 
		= "axiMListCri 요청 실패 => "+ err.message;
	});
	document.getElementById("resultArea2").innerHTML = '';
}//axiMListCri


//엔터키
function checkEnter(event){
	if (event.key === 'Enter') {
	        searchDB();
	    }
}


// 4. Ajax Check 검색기능
// => Check 검색 submit 을 Button(type 속성주의) 으로 변경
// => MemberController : axmcri 메서드 공유
// => 단, 조건 구분을 위해 요청명은 "/axmcheck"  

// 4.1) CheckClear
function checkClear(){
 let ck=document.querySelectorAll(".clear");
 for(let i = 0; i<ck.length(); i++){
	 ck[i].checked=false;
 }
 return false; // reset 의 기본이벤트 제거
}

// 4.2) Axios 요청처리
// => url : /member/axmcheck?currPage=1&rowsPerPage=5&check=1&check=2
// => MemberController 매핑 메서드는 'axmcri' 를 같이 사용함
function axiMListCheck(){
	let checkAll = document.querySelectorAll('.check');
	let checkData ='';
	
	// ** forEach() 적용 : check의 쿼리스트링 만들기 위함.
	checkAll.forEach(check =>{
		if(check.checked){
			checkData +='&check=' +check.value;
		}
	});
	
	// ** url 완성 후 Axios 요청
	// => 요청 : axiMListCri() 호출하기때문에 axmcheck 부터 시작
	let url = "axmcheck"
					+"?currPage1&rowsPerPage5"+checkData;
	axiMListCri(url); //Axios 요청
	
}//axiMListCheck










