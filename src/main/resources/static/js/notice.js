function noticewrite(){
    location.href = '/notice/write';
}

function noticesearch(){

    let searchcontent = $('.text').val();
    var contentPattern = /[a-zA-Z0-9가-힣]{2,}/;

    if(searchcontent.trim() == ""){
        alert("공백만 입력할수 없습니다.");
        return;
    }else if(!contentPattern.test(searchcontent)){
        alert("최소 2글자 이상입력하셔야합니다.")
        return;
    }
    document.information.submit();
}