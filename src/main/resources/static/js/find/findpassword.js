let errormessage = document.querySelector('#errormessage');


$(document).ready(function() {
    $('input[type=radio][name="FindType"]').change(function() {
        if($('#email').attr('type') == 'email'){
            $('#email').attr({'type':'text','placeholder':'가입된 폰번호(-없이 입력해주세요.)','id':'phone','name':'phone'});
            $('#phone').val('');
        } else if ($('#phone').attr('type') == 'text'){
            $('#phone').attr({'type':'email','placeholder':'가입이메일주소','id':'email','name':'email'});
            $('#email').val('');
        }
    });
});



function checkName(usernamenode) {
    var idPattern = /[a-zA-Z0-9]{5,10}/;
    if(usernamenode.value === "") {
        console.log(errormessage);
        errormessage.innerHTML = "아이디는 필수 정보입니다.";
        errormessage.style.color = "red";
        errormessage.style.fontSize = "12px";
        errormessage.style.fontFamily = "Noto Sans KR,sans-serif";
        errormessage.style.display = "block";
        return false;
    } else if(!idPattern.test(usernamenode.value)) {
        errormessage.innerHTML = "5~10자의 영어 대소문자,숫자만 입력해주세요.";
        errormessage.style.color = "red";
        errormessage.style.fontSize = "12px";
        errormessage.style.fontFamily = "Noto Sans KR,sans-serif";
        errormessage.style.display = "block";
        return false;
    } else {
        errormessage.style.display = "none";
        return true;
    }
}


function isEmailCorrect(form) {

    var emailPattern = /^[a-z0-9A-Z]{2,}@[a-z0-9A-Z]{2,}\.[a-zA-Z0-9]{2,3}$/i;

    if(email.value === ""){ 
        errormessage.innerHTML = "이메일은 필수 정보입니다.";
        errormessage.style.color = "red";
        errormessage.style.fontSize = "12px";
        errormessage.style.fontFamily = "Noto Sans KR,sans-serif";
        errormessage.style.display = "block";
        return;
    } else if(!emailPattern.test(form.value)) {
        errormessage.innerHTML = "사용 불가능한 이메일형식입니다.";
        errormessage.style.color = "red";
        errormessage.style.fontSize = "12px";
        errormessage.style.fontFamily = "Noto Sans KR,sans-serif";
        errormessage.style.display = "block";
        return;
    } else {
        errormessage.style.display = "none";
        return true;
    }

}


function checkPhoneNum(mobile) {
    var isPhoneNum = /([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})/;

    if(mobile.value === "") {
        errormessage.innerHTML = "전화번호는 필수 정보입니다.";
        errormessage.style.color = "red";
        errormessage.style.fontSize = "12px";
        errormessage.style.fontFamily = "Noto Sans KR,sans-serif";
        errormessage.style.display = "block";
        return ;
    } else if(!isPhoneNum.test(mobile.value)) {
        errormessage.innerHTML = "전화번호 형식이 맞지 않습니다.";
        errormessage.style.color = "red";
        errormessage.style.fontSize = "12px";
        errormessage.style.fontFamily = "Noto Sans KR,sans-serif";
        errormessage.style.display = "block";
        return;
    } else {
        errormessage.style.display = "none";
        return true;
    }

    }

/*
function findUserDiv(dto){
        console.log("안녕1");
        let tmp = '<div class="findusername_id_sec">';
        tmp += '<div class=class="completed_find_id">';
        tmp += '<p id="text_id">'+ dto.nickname + '회원님의 아이디는</p>';
        tmp += '<strong>' + dto.username + '<strong> 입니다.</div></div>';
    };
*/
let ajaxfindusernamebyemail = function(username,emailvalue){

    let emaildata = JSON.stringify({
                    "username":username,
                    "email":emailvalue});

     $.ajax({
         url : "/find/findpasswordbyemail",
         type : "post",
         data : emaildata,
         contentType: "application/json",
         success : function(result){
             if(result.dto != undefined){

                /*console.log(findUserDiv(result.dto));*/
                let tmp = '<div class="findusername_id_sec">';
                tmp += '<div class="completed_find_id">';
                tmp += '<p id="text_id">'+ result.dto.username + '회원님의 비밀번호는</p>';
                tmp += '<strong>' + result.dto.password + '<strong> 입니다.</div></div>';
                $('.findusername_id_sec').html(tmp);
                return;
             } else{
                alert('일치하는 회원정보가 없습니다.');
                return;
             }

         },
         error : function(){
                alert("잘못된 요청입니다. 다시 시도해주세요.");
                return;
             }
          })

}

let ajaxfindusernamebyphone = function(username,phonevalue){

    let phonedata = JSON.stringify({
                    "username":username,
                    "phone":phonevalue});

     $.ajax({
         url : "/find/findpasswordbyphone",
         type : "post",
         data : phonedata,
         contentType: "application/json",
         success : function(result){
             if(result.dto != undefined){

             let tmp = '<div class="findusername_id_sec">';
             tmp += '<div class="completed_find_id">';
             tmp += '<p id="text_id">'+ result.dto.username + '회원님의 비밀번호는</p>';
             tmp += '<strong>' + result.dto.password + '<strong> 입니다.</div></div>';
             $('.findusername_id_sec').html(tmp);

             } else {
             alert('일치하는 회원정보가 없습니다.');
             return;

             }

         },
         error : function(){
             alert("잘못된 요청입니다. 다시 시도해주세요.");
             return;
             }
          })

}


$('#findid').click(function(){

    let usernamenode = document.querySelector('#username');

    if(checkName(usernamenode) === false){
        return;
    }

    let username = $('#username').val();
    let useremail = $('#email').attr('id');
    let userphone = $('#phone').attr('id');
    let emailvalue = $('#email').val();
    let phonevalue = $('#phone').val();

    if(useremail != undefined){

        let form = document.querySelector("#email");
        isEmailCorrect(form);
        ajaxfindusernamebyemail(username,emailvalue);

    } else if(userphone != undefined){

        let mobile = document.querySelector('#phone');
        checkPhoneNum(mobile);
        ajaxfindusernamebyphone(username,phonevalue);
        
    }

});



