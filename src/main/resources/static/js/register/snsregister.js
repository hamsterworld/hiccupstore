'use strict';

const form = document.querySelector("#form__wrap"); //데이터를 전송하는 form
const checkAll = document.querySelector(".form_elements input"); //모두 동의 체크박스
const checkBoxes = document.querySelectorAll(".form_element input"); //각자 동의박스의 노드리스트
const submitButton = document.querySelector("#btnnextstep"); //다음버튼

const agreements = {
    termsOfService: false, //첫번째 필수동의 체크박스
    privacyPolicy: false, //두번째 필수동의 체크박스
  };

form.addEventListener("submit", (e) => e.preventDefault());
//각각체크 테스트
checkBoxes.forEach((item) => item.addEventListener("input", toggleCheckbox));

//버튼 활성화 비활성화하는 function
function toggleSubmitButton() {
    const { termsOfService, privacyPolicy } = agreements;
    console.log( { termsOfService, privacyPolicy } );
    if (termsOfService && privacyPolicy) {
      submitButton.disabled = false;
    } else {
      submitButton.disabled = true;
    }
  };

//모두체크하냐 모두체크안하냐
checkAll.addEventListener("click", (e) => {
    let  {checked}  = e.target; //target의 checked값을 가져오게된다.
    if (checked) {
      checkBoxes.forEach((item) => {
        item.checked = true;
        agreements[item.id] = true;
        item.parentNode.classList.add("active");
      });
    } else {
      checkBoxes.forEach((item) => {
        item.checked = false;
        agreements[item.id] = false;
        item.parentNode.classList.remove("active");
      });
    }
    toggleSubmitButton();
  });

//각자 체크마다의 이벤트
function toggleCheckbox(e) {
  const { checked, id } = e.target;
  agreements[id] = checked;
  this.parentNode.classList.toggle("active");
  toggleSubmitButton();
}



/* 버튼 페이지 이동 */
function prevpage(){
    window.location.href = '/'
}

function nextpage(){
    window.location.href = '/join/snsjoinform'
}
