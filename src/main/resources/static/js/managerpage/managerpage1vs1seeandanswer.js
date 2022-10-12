'use strict';

//각 버튼마다 이벤트를 건다.

function nextpage(){

oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
var boardcontent = document.getElementById("editorTxt").value;


if(byteCheck(boardcontent)){
    $("textarea[name='boardcontent']").val(boardcontent);
    document.information.submit();
}


}

function prevpage(){
    location.href='/managerpage/managerpage1vs1';
}

function byteCheck(content){

    if(content == "<p>&nbsp;</p>" || content.length < 9){
        alert("내용은 최소 5글자 이상이여야합니다.")
        return false;
    }
    return true;
}
