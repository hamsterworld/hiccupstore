'use strict';


function deleteboard(){

    if(confirm("정말 삭제하시겠습니까?")){

        document.information.submit();
}
};

function prevpage(){
    location.href = '/mypage/mypageproduct'
}