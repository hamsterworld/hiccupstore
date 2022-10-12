'use strict';

//주문내역 확인하기 버튼을 눌렀을 때
function checkOrder() {
    var orderId = parseInt($('#orderId').text());
    location.href = "/order/check?orderId="+orderId;
}
