'use strict';

function prevpage(){
    location.href = '/mypage/mypage1vs1'
}

function updatepage(){

    oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
    var boardcontent = document.getElementById("editorTxt").value;
    var boardtitle = $("#boardtitle").val();

    if(byteCheck(boardtitle,boardcontent)){
        $("textarea[name='boardcontent']").val(boardcontent);
        document.information.submit();
    }

}

function setThumbnail(event) {

document.querySelector("div#image_container").innerHTML = '';

    for (var image of event.target.files) {
          var reader = new FileReader();
          reader.onload = function(event) {
            var img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            document.querySelector("div#image_container").appendChild(img);
          };
          console.log(image);
          reader.readAsDataURL(image);
        }
}


function byteCheck(title,content){
    if(title.length < 5){
        alert("제목은 최소 5글자 이상이여야 합니다.")
        return false;
    } else if(content == "<p>&nbsp;</p>" || content.length < 9){
        alert("내용은 최소 5글자 이상이여야합니다.")
        return false;
    }
    return true;
}














