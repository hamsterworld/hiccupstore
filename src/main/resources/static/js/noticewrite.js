'use strict';

function prevpage(){
    location.href = '/notice'
}

function noticewrite(){

oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
var value = document.getElementById("editorTxt").value;

$("textarea[name='boardcontent']").val(value);
document.information.submit();

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
