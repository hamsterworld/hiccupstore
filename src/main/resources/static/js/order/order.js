'use strict';


$(document).on('click','.shipping', function(){
    var valueCheck = $('.shipping:checked').attr('id');
    if(valueCheck=="shippingBasic"){
        $('.d_form').css("background-color","#e2e2e2");
        $('.d_btn').attr("disabled",true);
        var a =  $('#userInfo >tbody').find("tr:eq(1)").find("td:eq(0)").text();
        $('#address').val(a);
    }else{
        $('.d_form').css("background-color","#ffffff");
        $('.d_btn').attr("disabled",false);
    }

});

$(document).on('focusout','#sample4_detailAddress', function(){
   setAddress();
});


//우편번호 api
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample4_postcode').value = data.zonecode;
            document.getElementById("sample4_roadAddress").value = roadAddr;

            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }

            setAddress();

        }
    }).open();
}

function setAddress(){
    var zCode = $('#sample4_postcode').val();
    var rAddr = $('#sample4_roadAddress').val();
    var dAddr = $('#sample4_detailAddress').val();
    $('#address').val("["+zCode+"] "+rAddr+" "+dAddr);
}


//직접 입력 메시지를 선택했을때 이벤트
function changeMessage() {
    var selectNumber = $('#messageBox').val();

    if(selectNumber == 5){
        $('#message').attr('readonly',false);
        $('#deliveryMessage').show();
    }else{
        $('#deliveryMessage').hide();
        $('#message').val("");
        $('#message').attr('readonly',true);
    }
}

//결제버튼 눌렀을 때
function OrderBtn(){

    var checkBox = document.getElementById('essential').checked;

    if(checkBox){
        var a = $('#phone').val();

        //물품정보
        var orderProducts = [];
        var trCount = $('#order_cart >tbody tr').length;

        for(let i=0;i<trCount;i++){
            var orderProduct = {"orderProductId":0};
            orderProduct.productId = parseInt($('#order_cart >tbody').find('tr:eq('+i+')').find('td:eq(0)').find('#orderProductId').val());
            orderProduct.orderId=0;
            orderProduct.quantity=parseInt($('#order_cart >tbody').find('tr:eq('+i+')').find('td:eq(1)').find('#orderQuantity').val());
            orderProduct.orderPrice=parseInt($('#order_cart >tbody').find('tr:eq('+i+')').find('td:eq(3)').find('#orderPrice').val());
            orderProducts.push(orderProduct);
        }


        //배송 주소
        var uri = ""+$('#packageInfo >tbody').find('tr:eq(2)').find('td:eq(0)').find('#address').val();
        var address =  encodeURI(uri);
        let csrfHeader = $('meta[name=_csrf_header]').attr('content');
        let csrfToken = $('meta[name=_csrf]').attr('content');

        $.ajax({
            url:"/order/orderProduct?address="+address,
            type:"post",
            data: JSON.stringify(orderProducts),
            contentType: "application/json; charset=utf-8",
            beforeSend : function(xhr){
                xhr.setRequestHeader(csrfHeader,csrfToken);
                xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
            },
            success: function(result){
                location.href = "/order/orderResult?orderId="+result;
            },error : function(jqXHR){
                alert(jqXHR.status);
            }
        });

    }else {
        alert("필수 항목에 동의해 주세요");
    }
}

