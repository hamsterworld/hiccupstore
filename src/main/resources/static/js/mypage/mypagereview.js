

$('.js_pr_btn_comment').click(function selectcomment(){

    let boardid = $(this).attr('data_boardid');
    let close = $(this).nextAll('.close');
    let ajax_comment = $(this);
    console.log(ajax_comment);
    let data = {boardid: boardid};
    let csrfHeader = $('meta[name=_csrf_header]').attr('content');
    let csrfToken = $('meta[name=_csrf]').attr('content');

    $.ajax({
       	url : "/mypage/searchcomment",
       	type : "post",
       	data : data,
       	beforeSend : function(xhr){
       	    xhr.setRequestHeader(csrfHeader,csrfToken);
       	    xhr.setRequestHeader("x-Requested-With","XMLHttpRequests");
       	},
       	success : function(result){
            $('.framentcomment'+boardid).replaceWith(result);
            close.css('display','inline');
            ajax_comment.css('display','none');
       	},
       	error : function(){
       		alert("잘못된 요청입니다. 다시 시도해주세요.");
       	    }
        });

});

$('.js_pr_btn_comment_close').click(function displaynonecomment(){
    let boardid = $(this).attr('data_boardid');
    $(this).css('display','none');
    $(this).prev('.open').css('display','inline');
    $(this).parent().parent().parent().next('#commentnull').css('display','none');
    $(this).parent().parent().parent().next('#commentTable').css('display','none');

});

$('.js_pr_btn_comment_open').click(function displayblockcomment(){
    let boardid = $(this).attr('data_boardid');
    $(this).css('display','none');
    $(this).next('.close').css('display','inline');
    $(this).parent().parent().parent().next('#commentnull').css('display','block');
    $(this).parent().parent().parent().next('#commentTable').css('display','block');

});