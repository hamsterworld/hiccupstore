function prevpage(){
    location.href = '/notice'
}

function noticedelete(){
    let val = $('#hidden_noticedid').val();
    location.href='/notice/delete/'+val;
}

function noticeupdate(){
    let val = $('#hidden_noticedid').val();
    location.href='/notice/update/'+val;
}