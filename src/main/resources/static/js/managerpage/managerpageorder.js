'use strict';

let today = new Date();   

const year = today.getFullYear();
const month = today.getMonth();
const day = today.getDate();

let todayvalue = today.toISOString().substring(0,10);

let beforeWeekvalue = new Date(year,month,day-7).toISOString().substring(0,10);
let beforeTwoWeekvalue = new Date(year,month,day-15).toISOString().substring(0,10);
let beforeMonthalue = new Date(year,month-1,day).toISOString().substring(0,10);
let beforeThreeMonthvalue = new Date(year,month-3,day).toISOString().substring(0,10);
let beforeYearhvalue = new Date(year-1,month,day).toISOString().substring(0,10);

if($("#startdate").val()== '' && $("#lastdate").val()== ''){
    $("#startdate").val(todayvalue);
    $("#lastdate").val(todayvalue);
}

let testfunction = function(){
    if($("#startdate").val()== todayvalue && $("#lastdate").val()== todayvalue){
        $("button[data-value='0']").addClass('on');
        return;
    }

    if($("#startdate").val()== beforeWeekvalue && $("#lastdate").val()== todayvalue){
        $("button[data-value='7']").addClass('on');
        return;
    }

    if($("#startdate").val()== beforeTwoWeekvalue && $("#lastdate").val()== todayvalue){
        $("button[data-value='15']").addClass('on');
        return;
    }

    if($("#startdate").val()== beforeMonthalue && $("#lastdate").val()== todayvalue){
        $("button[data-value='30']").addClass('on');
        return;
    }

    if($("#startdate").val()== beforeThreeMonthvalue && $("#lastdate").val()== todayvalue){
        $("button[data-value='90']").addClass('on');
        return;
    }

    if($("#startdate").val()== beforeYearhvalue && $("#lastdate").val()== todayvalue){
        $("button[data-value='365']").addClass('on');
        return;
    }
}

testfunction();

//** 시간다루기 함수 */
$("button[data-value]").click(function(e){

    if($(this).attr('data-value') == 0){
        $("#startdate").val(todayvalue);
        $("#lastdate").val(todayvalue);
    } else if($(this).attr('data-value') == 7){
        $("#startdate").val(beforeWeekvalue);
        $("#lastdate").val(todayvalue);
    } else if($(this).attr('data-value') == 15){
        $("#startdate").val(beforeTwoWeekvalue);
        $("#lastdate").val(todayvalue);
    } else if($(this).attr('data-value') == 30){
        $("#startdate").val(beforeMonthalue);
        $("#lastdate").val(todayvalue);
    } else if($(this).attr('data-value') == 90){
        $("#startdate").val(beforeThreeMonthvalue);
        $("#lastdate").val(todayvalue);
    } else if($(this).attr('data-value') == 365){
        $("#startdate").val(beforeYearhvalue);
        $("#lastdate").val(todayvalue);
    }

    if($(this).attr('class') == 'on'){
        return;
    } else if($(this).attr('class') == ''){
        $(".on").removeClass('on');
        $(this).addClass('on');
        return;
    }

});

$(".btn_date_check").click(function(){
    document.information.submit();
})
//각 버튼마다 이벤트를 건다.

$(".btn_board_search").click(function(){
    document.frmList.submit();
})


$(".statuschanged").change(function(){

        let confirmed = confirm('주문상태를 변경하시겠습니까?');
        let csrfHeader = $('meta[name=_csrf_header]').attr('content');
        let csrfToken = $('meta[name=_csrf]').attr('content');

        if(confirmed){

                let orderid = $(this).attr('data_orderid');
                let orderstatus = $(this).val(); // input_id

                if( orderstatus == 'noselected'){
                    alert('선택으로 상태를 변경할수없습니다.');
                    return;
                }

                let data = JSON.stringify({orderstatus: orderstatus,orderid: orderid});

                $.ajax({
               	url : "/managerpage/changedorderstatus",
               	type : "post",
               	data : data,
               	contentType: "application/json",
               	beforeSend : function(xhr){
               	    xhr.setRequestHeader(csrfHeader,csrfToken);
               	    xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
               	},
               	success : function(result){
                    alert("정상적으로 변경되었습니다.");
               	},
               	error : function(){
               		alert("잘못된 요청입니다. 다시 시도해주세요.");
               	    }
                })
        } else {
             alert("변경을 취소하였습니다.");
        }
});



$(".refundstatuschanged").change(function(){

        let confirmed = confirm('주문상태를 변경하시겠습니까?');
        let csrfHeader = $('meta[name=_csrf_header]').attr('content');
        let csrfToken = $('meta[name=_csrf]').attr('content');



        if(confirmed){

                let orderid = $(this).attr('data_orderid');
                let orderstatus = $(this).val(); // input_id

                if( orderstatus == 'noselected'){
                    alert('선택으로 상태를 변경할수없습니다.');
                    return;
                }
                let data = JSON.stringify({orderstatus: orderstatus,orderid: orderid});

                $.ajax({
               	url : "/managerpage/refundchangedorderstatus",
               	type : "post",
               	data : data,
               	contentType: "application/json",
               	beforeSend : function(xhr){
               	    xhr.setRequestHeader(csrfHeader,csrfToken);
               	    xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
               	},
               	success : function(result){
                    alert("정상적으로 변경되었습니다.");
               	},
               	error : function(){
               		alert("잘못된 요청입니다. 다시 시도해주세요.");
               	    }
                })
        } else {
             alert("변경을 취소하였습니다.");
        }
});
