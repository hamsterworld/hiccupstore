'use strict';

const error = document.querySelectorAll('.error_next_box');



    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }

/* 아이디 확인 callback 함수 */


let id = document.querySelector('.input_id');
//var result = foo();

//foo(function(result) {
//    // Code that depends on 'result'
//});
//
//function myCallback(result) {
//    // Code that depends on 'result'
//}

//foo(myCallback);
//
//function foo(callback) {
//    $.ajax({
//        // ...
//        success: function(response) {
//            // For example, filter the response
//            callback(filtered_response);
//        }
//    });
//}

id.addEventListener("focusout", checkId);

function checkId() {
    let flag ;
    var idPattern = /[a-zA-Z0-9]{5,10}/;
    if(id.value === "") {

        error[0].innerHTML = "필수 정보입니다.";
        error[0].style.color = "red";
        error[0].style.fontSize = "12px";
        error[0].style.fontFamily = "Noto Sans KR,sans-serif";
        error[0].style.display = "block";
        error[0].style.marginTop = "15px";
        return false;

    } else if(!idPattern.test(id.value)) {

        error[0].innerHTML = "5~10자의 영문 소대문자, 숫자만 사용 가능합니다.";
        error[0].style.color = "red";
        error[0].style.fontSize = "12px";
        error[0].style.fontFamily = "Noto Sans KR,sans-serif";
        error[0].style.display = "block";
        error[0].style.marginTop = "15px";
        return false;

    } else {

        let username = $('.input_id').val(); // input_id
        let data = JSON.stringify({username: username});
        let csrfHeader = $('meta[name=_csrf_header]').attr('content');
        let csrfToken = $('meta[name=_csrf]').attr('content');

        $.ajax({
       	url : "/join/searchUserName",
       	type : "post",
       	data : data,
       	async: false,
       	contentType: "application/json",
       	beforeSend : function(xhr){
       	    xhr.setRequestHeader(csrfHeader,csrfToken);
       	    xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
       	},
       	success : function(result){
       		if(result == 'false'){
       		    console.log("중복된아이디")
       			error[0].innerHTML = "중복된 아이디입니다.";
                error[0].style.color = "#08A600";
                error[0].style.fontSize = "12px";
                error[0].style.fontFamily = "Noto Sans KR,sans-serif";
                error[0].style.display = "block";
                error[0].style.marginTop = "15px";
       			error[0].style.color = "red";
       			flag = false;
       		} else if(result == 'true'){
       		    console.log("사용가능한 아이디")
       		    error[0].innerHTML = "사용 가능한 아이디입니다.";
                error[0].style.color = "#08A600";
                error[0].style.fontSize = "12px";
                error[0].style.fontFamily = "Noto Sans KR,sans-serif";
                error[0].style.display = "block";
                error[0].style.marginTop = "15px";
       			error[0].style.color = "green";
       			flag = true;
       		}
       	},
       	error : function(){
       		alert("잘못된 요청입니다. 다시 시도해주세요.");
       	    }
        }) //ajax 끝
        return flag;
    } //else 끝
} //ischeckid 끝



/* 비밀번호 callback 함수 */

let pw1 = document.querySelector('.input_pw');
let pwimg1 = document.querySelector('.icon_password');

pw1.addEventListener("focusout", checkPw);


function checkPw() {
    var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
    if(pw1.value === "") {
        error[1].innerHTML = "필수 정보입니다.";
        error[1].style.color = "red";
        error[1].style.fontSize = "12px";
        error[1].style.fontFamily = "Noto Sans KR,sans-serif";
        error[1].style.display = "block";
        error[1].style.marginTop = "15px";
        pwimg1.src = "/image/m_icon_not_use.png";
        return false;
    } else if(!pwPattern.test(pw1.value)) {
        error[1].innerHTML = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
        error[1].style.color = "red";
        error[1].style.fontSize = "12px";
        error[1].style.fontFamily = "Noto Sans KR,sans-serif";
        error[1].style.display = "block";
        error[1].style.marginTop = "15px";
        pwimg1.src = "/image/m_icon_not_use.png";
        return false;
    } else {
        error[1].style.display = "none";
        pwimg1.src = "/image/m_icon_safe.png";
        return true;
    }
}

let pw2 = document.querySelector('.input_pw_confirm');
let pwimg2 = document.querySelector('.icon_password2');

pw2.addEventListener("focusout", comparePw);

function comparePw() {
    if(pw2.value === pw1.value && pw2.value != "") {
        //pwImg2.src = "m_icon_check_enable.png";
        error[2].style.display = "none";
        pwimg2.src = "/image/m_icon_check_enable.png";
        return true;
    } else if(pw2.value !== pw1.value) {
        //pwImg2.src = "m_icon_check_disable.png";
        error[2].innerHTML = "비밀번호가 일치하지 않습니다.";
        error[2].style.color = "red";
        error[2].style.fontSize = "12px";
        error[2].style.fontFamily = "Noto Sans KR,sans-serif";
        error[2].style.display = "block";
        error[2].style.marginTop = "15px";
        pwimg2.src = "/image/m_icon_not_use.png";
        return false;
    } 
    if(pw2.value === "") {
        error[2].innerHTML = "필수 정보입니다.";
        error[2].style.color = "red";
        error[2].style.fontSize = "12px";
        error[2].style.fontFamily = "Noto Sans KR,sans-serif";
        error[2].style.display = "block";
        error[2].style.marginTop = "15px";
        pwimg2.src = "/image/m_icon_not_use.png";
        return false;
    }
}





/* 이름 callback 함수 */

let userName = document.querySelector('.input_name');

userName.addEventListener("focusout", checkName);

function checkName() {
    var namePattern = /[가-힣]/;
    if(userName.value === "") {
        error[3].innerHTML = "필수 정보입니다.";
        error[3].style.color = "red";
        error[3].style.fontSize = "12px";
        error[3].style.fontFamily = "Noto Sans KR,sans-serif";
        error[3].style.display = "block";
        error[3].style.marginTop = "15px";
        return false;
    } else if(!namePattern.test(userName.value) || userName.value.indexOf(" ") > -1) {
        error[3].innerHTML = "초성제외 한글만 입력해주세요.";
        error[3].style.color = "red";
        error[3].style.fontSize = "12px";
        error[3].style.fontFamily = "Noto Sans KR,sans-serif";
        error[3].style.display = "block";
        error[3].style.marginTop = "15px";
        return false;
    } else {
        error[3].style.display = "none";
        return true;
    }
}

/* email callback 함수 */

const form = document.querySelector("#email");
form.addEventListener("focusout", isEmailCorrect);


function isEmailCorrect() {
    let flag;
    var emailPattern = /^[a-z0-9A-Z]{2,}@[a-z0-9A-Z]{2,}\.[a-zA-Z0-9]{2,3}$/i;

    if(email.value === ""){ 
        error[4].innerHTML = "필수 정보입니다.";
        error[4].style.color = "red";
        error[4].style.fontSize = "12px";
        error[4].style.fontFamily = "Noto Sans KR,sans-serif";
        error[4].style.display = "block";
        error[4].style.marginTop = "15px";
        return false;
    } else if(!emailPattern.test(email.value)) {
        error[4].innerHTML = "사용 불가능한 이메일형식입니다.";
        error[4].style.color = "red";
        error[4].style.fontSize = "12px";
        error[4].style.fontFamily = "Noto Sans KR,sans-serif";
        error[4].style.display = "block";
        error[4].style.marginTop = "15px";
        return false;
    } else {

        let email = $("#email").val(); // input_id
        let data = JSON.stringify({email: email});
        let csrfHeader = $('meta[name=_csrf_header]').attr('content');
        let csrfToken = $('meta[name=_csrf]').attr('content');
        $.ajax({
        url : "/join/searchEmail",
        type : "post",
        async: false,
        data : data,
        contentType: "application/json",
        beforeSend : function(xhr){
            xhr.setRequestHeader(csrfHeader,csrfToken);
            xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
        },
        success : function(result){
        	if(result == 'false'){
        		error[4].innerHTML = "이미 가입된 이메일입니다.";
                error[4].style.color = "#08A600";
                error[4].style.fontSize = "12px";
                error[4].style.fontFamily = "Noto Sans KR,sans-serif";
                error[4].style.display = "block";
                error[4].style.marginTop = "15px";
        		error[4].style.color = "red";
        	    flag =  false;
        	} else if(result == 'true'){
        	    error[4].innerHTML = "사용 가능한 이메일입니다.";
                error[4].style.color = "#08A600";
                error[4].style.fontSize = "12px";
                error[4].style.fontFamily = "Noto Sans KR,sans-serif";
                error[4].style.display = "block";
                error[4].style.marginTop = "15px";
        		error[4].style.color = "green";
        		flag =  true;
        	}
        },
        error : function(){
        	alert("잘못된 요청입니다. 다시 시도해주세요.");
            }
        })

    return flag;

    }


}

function changevalue(value){
    
    let storage = form.value;

    let emailcontentindex = storage.indexOf('@');

    if(emailcontentindex != -1){
        storage = storage.substring(0,emailcontentindex);
    }

    storage += value;

    form.value=storage;

    isEmailCorrect();
}

/* 전화번호 callback 함수 */

let mobile = document.querySelector('.input_num');

mobile.addEventListener("focusout", checkPhoneNum);

function checkPhoneNum() {
    let flag;
    var isPhoneNum = /([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})/;

    if(mobile.value === "") {
        error[5].innerHTML = "필수 정보입니다.";
        error[5].style.color = "red";
        error[5].style.fontSize = "12px";
        error[5].style.fontFamily = "Noto Sans KR,sans-serif";
        error[5].style.display = "block";
        error[5].style.marginTop = "15px";
        return false;
    } else if(!isPhoneNum.test(mobile.value)) {
        error[5].innerHTML = "전화번호 형식이 맞지 않습니다.";
        error[5].style.color = "red";
        error[5].style.fontSize = "12px";
        error[5].style.fontFamily = "Noto Sans KR,sans-serif";
        error[5].style.display = "block";
        error[5].style.marginTop = "15px";
        return false;
    } else {

        let mobile = $('.input_num').val(); // input_id
        let data = JSON.stringify({mobile: mobile});
        let csrfHeader = $('meta[name=_csrf_header]').attr('content');
        let csrfToken = $('meta[name=_csrf]').attr('content');
        $.ajax({
        url : "/join/searchMobile",
        type : "post",
        data : data,
        async: false,
        contentType: "application/json",
        beforeSend : function(xhr){
            xhr.setRequestHeader(csrfHeader,csrfToken);
            xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
        },
        success : function(result){
        	if(result == 'false'){
        		error[5].innerHTML = "이마 가입된 전화번호입니다.";
                error[5].style.color = "#08A600";
                error[5].style.fontSize = "12px";
                error[5].style.fontFamily = "Noto Sans KR,sans-serif";
                error[5].style.display = "block";
                error[5].style.marginTop = "15px";
        		error[5].style.color = "red";
        		flag = false;

        	} else if(result == 'true'){
        	    error[5].innerHTML = "사용가능한 전화번호입니다.";
                error[5].style.color = "#08A600";
                error[5].style.fontSize = "12px";
                error[5].style.fontFamily = "Noto Sans KR,sans-serif";
                error[5].style.display = "block";
                error[5].style.marginTop = "15px";
        		error[5].style.color = "green";
        		flag = true;
        	}
        },
        error : function(){
        	alert("잘못된 요청입니다. 다시 시도해주세요.");
            }
        })

        return flag;
    }

    
}


/* 주소 callback 함수 */


let postcode = document.querySelector('#sample6_postcode');
let address = document.querySelector('#sample6_address');
let detailaddress = document.querySelector('#sample6_detailAddress');

detailaddress.addEventListener("focusout", checkaddress);

function checkaddress() {

    var addressPattern = /[\{\}\[\]\/?.,;:|\)*~`!^\+┼<>@\#$%&\'\"\\\(\=]/;

    if(postcode.value === "" || address.value === "" || detailaddress.value === "") {
        error[6].innerHTML = "필수 정보입니다.";
        error[6].style.color = "red";
        error[6].style.fontSize = "12px";
        error[6].style.fontFamily = "Noto Sans KR,sans-serif";
        error[6].style.display = "block";
        error[6].style.marginTop = "10px";
        return false;

    } else if(addressPattern.test(detailaddress.value)) {

        error[6].innerHTML = "특수문자는 입력하실수 없습니다.";
        error[6].style.color = "red";
        error[6].style.fontSize = "12px";
        error[6].style.fontFamily = "Noto Sans KR,sans-serif";
        error[6].style.display = "block";
        error[6].style.marginTop = "10px";
        return false;

    }else {
        error[6].style.display = "none";
        return true;
    }


}



/* 생년월일 callback 함수 */

let yy = document.querySelector('#yy');
let dd = document.querySelector('#dd');

let pattern_num = /[0-9]/;

yy.addEventListener("focusout", checkYear);
dd.addEventListener("focusout", checkDay);

function checkYear() {

    //isBirthEntered();

    if(yy.value.length !== 4 || !pattern_num.test(yy.value)) {
        error[7].innerHTML = "태어난 년도 4자리를 정확하게 입력하세요.";
        error[7].style.color = "red";
        error[7].style.fontSize = "12px";
        error[7].style.fontFamily = "Noto Sans KR,sans-serif";
        error[7].style.display = "block";
        error[7].style.marginTop = "10px";
        return false;
    } else if (parseInt(yy.value) < 1950) {
        error[7].innerHTML = "오늘내일 하시는분인데??";
        error[7].style.color = "red";
        error[7].style.fontSize = "12px";
        error[7].style.fontFamily = "Noto Sans KR,sans-serif";
        error[7].style.display = "block";
        error[7].style.marginTop = "10px";
        return false;
    } else if (parseInt(yy.value) >= 2003){
        error[7].innerHTML = "미성년자는 술 못사는데ㅋㅋ?";
        error[7].style.color = "red";
        error[7].style.fontSize = "12px";
        error[7].style.fontFamily = "Noto Sans KR,sans-serif";
        error[7].style.display = "block";
        error[7].style.marginTop = "10px";
        return false;
    } else {
        error[7].style.display = "none";
        return true;
    }

}

function checkDay() {

    //isBirthEntered();

    if(!pattern_num.test(dd.value)) {
        error[7].innerHTML = "숫자를 입력하세요.";
        error[7].style.color = "red";
        error[7].style.fontSize = "12px";
        error[7].style.fontFamily = "Noto Sans KR,sans-serif";
        error[7].style.display = "block";
        error[7].style.marginTop = "10px";
        return false;
    } else if (parseInt(dd.value) >= 32) {
        console.log("2");
        error[7].innerHTML = "존재할수가없는데?";
        error[7].style.color = "red";
        error[7].style.fontSize = "12px";
        error[7].style.display = "block";
        error[7].style.fontFamily = "Noto Sans KR,sans-serif";
        error[7].style.marginTop = "10px";
        return false;
    } else {
        error[7].style.display = "none";
        return true;
    }

}


/* 전송 검증하기 */


//document.getElementById("btnnextstep").addEventListener("click", movesearchform);
//document.getElementById("btn1").addEventListener("keyup", (e) => searchalcohol(e));

function insertinformation(){
    if(!checkId()){
        id.focus();
        //$("#header").attr("tabindex", -1).focus(); //div에 포커스 거는방법
        return false;
    } else if(!checkPw()){
        pw1.focus();
        return false;
    } else if(!comparePw()){
        pw2.focus();
        return false;
    } else if(!checkName()){
        userName.focus();
        return false;
    } else if(!isEmailCorrect()){
        form.focus();
        return false;
    } else if(!checkPhoneNum()){
        mobile.focus();
        return false;
    } else if(!checkaddress()){
        detailaddress.focus();
        return false;
    } else if( yy.value != '' || dd.value != ''){
        if(!checkYear()){
            yy.focus();
            return false;
        } else if(!checkDay()){
            dd.focus();
            return false;
        }
    }
    document.information.submit();
}


function prevpage(){
    window.location.href = '/join'
}
