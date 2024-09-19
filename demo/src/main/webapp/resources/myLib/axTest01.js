/**
    ** Ajax Test 01 : Login
    => fetch 와 axios 비교
    => request Data Type (Json)
    => response Data Type (Text, Json)
    
    *** fetch
    => fetch(url, [options])
    -> url: 액세스할 URL
    -> options – 선택적 매개변수: 메소드, 헤더 등
    -> fetch 함수의 응답(Response) 처리는 일반적으로 2단계로 함.
    -> 실행후 promise객체를 return 하므로, .then() .catch() .finally() 등의 메서드체인 적용됨. 
    
    => Response 의 Property
    -> response.status: HTTP 상태 코드(예: 200)
    -> response.ok: Boolean, HTTP 상태 코드가 200-299 사이 이면 True
    -> response.headers: HTTP 헤더가 있는 맵과 유사한 객체 
    
    ** Promise
    => 비동기 처리에서 동기식(순차적) 처리를 지원해줌.
        즉, 지연함수 와 비동기연산(Ajax) 을 제어할 수 있도록 해주는 객체. 
       ( 지연함수: 지정한 시간 후에 실행 되도록 정의한 함수 )
    => 기본형식
    promise.then(function(count){
                console.log("** Test2) resolve count => "+count); 
            }).catch(function(message){
                console.log("** Test2) reject message => "+message); 
            }).finally(function(){
                console.log("** Test2) finally Test"); 
            });    
        
 */

	
	

"use strict"
//1. Login
//1.1) loginForm 출력
function rsLoginf() {
	let resultHtml =
		`<table align center>
        <caption><h3 style="box-shadow: inset 0 -10px 0  #CEDF9F;">🧸 Ajax Login Form 🧸</h3></caption>
        <tr height=40><td bgcolor="#CEDF9F"><label for="id">ID</label></td>
            <td><input type="text" name="id" id="id"></td>
        </tr>
        <tr height=40><td bgcolor="#CEDF9F"><label for="password">Password</label></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
        <tr height=40>
            <td colspan="2"><span class="textlink" onclick="rsLogin()">rsLogin</span>&nbsp;&nbsp;
                <span class="textlink" onclick="rsLoginjj()">rsLoginJJ</span>&nbsp;&nbsp;
                <span class="textlink" onclick="axiLoginjj()">axiLoginJJ</span>&nbsp;&nbsp;
				<span class="textlink" onclick="axiLoginjj2()">axiLoginJJ2</span>&nbsp;&nbsp;
                <input type="reset" value="취소">
            </td>
        </tr>
    </table>`;

	document.getElementById('resultArea1').innerHTML = resultHtml;
}

// 1.2) Login Service 요청 & 처리 (비동기 처리) 
// 웹페이지를 전달하는 것이 아닌 성공 실패 여부만 전달받으면 됨.
// -> Controller : page 를 respons 에 담는 것이 아니고, 결과 값만 담으면 됨.
// -> 위와 같은 기능을 처리하는 전용 Controller를 RestController  
//	** Ajax 요청, fetch 적용
// =>  Server : @RestController, 계층적 uri 적용, Post 요청
// => Data Type : request : JSON -> response : Text   

function rsLogin() {
	let url = "/rest/rslogin";
	fetch(url, {
		method: 'Post',
		body: JSON.stringify({
			id: document.getElementById("id").value,
			password: document.getElementById("password").value
		}),
		headers: { 'Content-Type': "application/json" }
		// => POST 요청에서는 반드시 headers 형식 작성 
		//    (JSON 포맷을 사용함을 알려줘야함)

	}).then(response => {

		// ** then 1 단계
		// => status 확인 
		//        -> 성공: Response Body-reading 후 Data return
		//        -> 실패: Error 생성후 catch 블럭으로  
		// => fetch는 네트워크 장애등으로 HTTP요청을 할수없거나,
		//      url에 해당하는 페이지가 없는 경우에만 거부(reject)되어 catch로 분기하므로,
		//      .then절(1단계) 에서 수동으로 HTTP 에러를 처리함.
		//      그러나 axios는 상태코드가 2xx의 범위를 넘어가면 거부(reject)함.

		if (!response.ok) throw new Error(response.status);   //상태확인
		return response.text();	// 성공하면 성공내용을 다음 then에 전달
		// => 서버에서 Text 형식으로 보냈으므로  text() 메서드 사용
		//   ( Type 별로 Body-reading method를 적용함 ) 
		/* => Body-reading method
		• response.text() – 응답을 읽고 텍스트로 반환
		• response.json() – 응답을 JSON으로 구문 분석
		• response.formData() – 응답을 FormData객체로 반환
		• response.blob() – 응답을 Blob (유형이 있는 이진 데이터) 으로 반환
		• response.arrayBuffer() – 응답을 ArrayBuffer (바이너리 데이터의 저수준 표현) 로 반환
		• response.body - ReadableStream 객체이므로 본문을 청크별로 읽을 수 있다.
		*/
	}).then(responseData => {  // responseData 인자에 1단계인 response.text() 가 들어감. 
		// ** then 2 단계
		// => 1 단계에서 return 한 Data 처리
		alert(`** responseData =>  ${responseData}`);
		location.reload();

	}).catch(err => { //1단계의 new Error(response.status) 가 err의 message 인자에 들어감.
		console.error(`** Error => ${err.message}`);
		if (err.message == '502') {
			alert(`Id 또는 password 오류 입니다. 다시 시도하세요.`);
		} else {
			alert(`System 오류, 잠시 후 다시하세요. status => ${err.message}`);
		}
	});

}//rsLogin

// 1.3) rsLoginjj 
// => 요청은 fetch 
// => request: JSON, response: JSON

function rsLoginjj() {
	let url = 'rest/rsloginjj';
	fetch(url, {
		method: 'Post',
		body: JSON.stringify({
			id: document.getElementById("id").value,
			password: document.getElementById("password").value
		}),
		headers: { 'Content-Type': "application/json" }
	}).then(response => {
		if (!response.ok) throw new Error(response.status);   //상태확인
		return response.json();
		// => 서버에서 JSON 형식으로 보냈으므로 json() 메서드 사용
	}).then(responseData => {
		alert(`** responseData : id =  ${responseData.id}, name =  ${responseData.username}`);
		location.reload();

	}).catch(err => {
		console.error(`** Error => ${err.message}`);
		if (err.message == '502') {
			alert(`Id 또는 password 오류 입니다. 다시 시도하세요.`);
		} else {
			alert(`System 오류, 잠시 후 다시하세요. status => ${err.message}`);
		}
	});
}

//	** Ajax 요청, Axios 적용

// ** Ajax 요청, Axios 적용
// => 라이브러리 추가 (CDN 으로..   axTestForm.jsp 에)
// => 서버요청은 위 "1.3) rsLoginJJ" 과 동일하게 rsloginjj 
// => JSON <-> JSON


// => Request
//    - data  : JSON Type 기본 (fetch 처럼 JSON.stringify 필요없음) 
//    - headers: {'Content-Type': 'application/json'}  
// => Response
//     - then : 응답 Data는 매개변수.data 로 접근가능, JSON Type 기본 (1단계로 모두 받음: fetch 와 차이점))   
//    - catch
//        . axios는 상태코드가 2xx의 범위를 넘어가면 거부(reject) 되어 catch절로 분기함 
//          이때 catch 절의 매개변수는 response 속성으로 error 내용 전체를 객체형태로 전달받음    
//        . error.response : error 내용 전체를 객체형태로 전달받음
//        . error.response.status : status 확인가능    
//        . error.message : 브라우져의 Error_Message, "Request failed with status code 415

// 1.4) axiLoginJJ
function axiLoginjj() {
	let url = '/rest/rsloginjj';

	axios({
		url: url,
		method: 'Post',
		Headers: { 'Content-Type': 'application/json' },
		data: {
			id: document.getElementById("id").value,
			password: document.getElementById("password").value
		}
	}).then(response => {
		alert(`** response.data => ${response.data}`);
		alert(`** response: id = ${response.data.id}, name = ${response.data.username}`);
		location.reload();
	}).catch(err => { //err 객체가 넘어옴.
		console.error(`** err.response = ${err.response},
						err.response.status = ${err.status},
						err.message = ${err.message}`);
		if (err.response.status == '502') alert(`id 또는 password 오류 입니다.!! 다시하세요~~`);
		else alert (`System 오류, 잠시 후 다시하세요. status => ${err.message}`)
	});
}







