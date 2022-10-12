'use strict';

let today = new Date();

const year = today.getFullYear();
const month = today.getMonth();
const day = today.getDate();
const hour = today.getHours();
const min = today.getMinutes();

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
        // document.getElementById("startdate").value = todayvalue;
        // document.getElementById("lastdate").value = todayvalue;
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