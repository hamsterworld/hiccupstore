# HiccupStore Project

<img src="https://user-images.githubusercontent.com/91558193/195794514-d6f2a6c3-ab52-45f0-9842-5cdd57f8a140.png">
우리나라 전통술을 판매하는 웹사이트를 만들어보고 호스팅해보는 것이 목적인 Project입니다.

<br/>
<br/>

### ⏰ 소요 기간
2022.07.21 - 2022.10.05

<br/>

## 📚STACKS
<img src="https://img.shields.io/badge/spring boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/spring security-6DB33F?style=flat-square&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/Java-FF160B?style=flat-square&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"/> <img src="https://img.shields.io/badge/Oauth2-304CB2?style=flat-square&logo=Oauth2&logoColor=white"/>  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/Git hub-181717?style=flat-square&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=Amazon AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white"/> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=white"/> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=Thymeleaf&logoColor=white"/>

<br/>
<br/>

### 📜 프로젝트를 통해 얻은 경험
- AWS를 이용해 우리팀원들이 제작한 웹사이트를 호스팅해보는 경험을 얻었던 프로젝트 입니다.<br/>
- 클라우드 서비스를 이용하면서 S3,VPC,RDS,Auto Scaling,ELB,EC2 와같이 다양한 AWS서비스를 적용해볼 수 있었습니다.<br/>
- Oauth2를 이용하여 SNS인증로그인을 경험해볼수있었습니다.<br/>
- CSRF,SQL INJECTION,고정세션방어와같이 기본적인 웹보안을 경험해볼수있었습니다.<br/>
- Spring FrameWork를 사용하여, 웹사이트를 구축해보는 경험을 얻을 수 있었습니다.<br/>

<br/>


### 🏅 프로젝트 내 역할 및 기여도
- UserDomain 담당
  - MyPage 구현
    - 1대1문의
    - 주문조회
    - 최근 본 상품
    - 리뷰조회
    - 상품문의
    - 회원탈퇴
    - 회원정보수정
  - ManagerPage 구현
    - 고객주문조회
    - 1대1문의답변
    - 상품문의답변
  - 로그인,회원가입 구현
  - SNS로그인 구현
- AWS 인프라 구축
- 그외
  - index Page 구현
  - 공지사항 게시판 구현

<br/>
<br/>

### 📃 유스케이스 다이어그램

<br/>

![UseCase Diagram](https://user-images.githubusercontent.com/91558193/195800975-3543f094-46a4-4001-a26f-9979dc627e87.png)


<br/>
<br/>

### 📃 ERD

<br/>

![ERD 다이어그램](https://user-images.githubusercontent.com/91558193/195801462-799b3c7a-ce69-4b80-8ea1-ef778e05aab8.png)


<br/>
<br/>

### 📃 AWS architecture

<br/>

![HiccupStoreAWS아키텍처](https://user-images.githubusercontent.com/91558193/195802567-031cb872-8a85-457e-9a8f-0f6cdd89829d.jpg)

<br/>
<br/>


### 🖋️ 프로젝트 후기

<br/>

  길었던 프로젝트를 마무리하면서 느꼈던 점은 2가지입니다.

- 프로젝트를 진행하면서 저희는 게시판에 파일업로드기능을 먼저 Local에 업로드할수있게 만들어놓
  고, 그다음 AWS S3버킷에 업로드하도록 계획했었습니다.
  그러나 프로젝트를 진행하면서 저희의 FileUpload관련 모듈을 객체지향적으로 설계하지못했습니다.
  그 결과로, FileUpload모듈을 Local에서 S3버킷으로 변경하기위해 기존의 FileUpload패키지를 직접
  전부 수정해야했고, 그과정에서 FileUpload를 사용하는 패키지들이 충돌이 발생하였으며,프로젝트의
  생산성이 현저히 떨어지게되었습니다.
  Java는 객체지향적인 언어입니다. 객체지향적인 언어는 변화에 유연하다는 특징을 가지고있습니다.
  그러나 저희 프로젝트의 코드는 객체지향적이지 못했으며,변화에 유연하지 못했고 이는 생산성저하
  라는 결과를 내게 되었습니다. 앞으로 프로젝트에서 객체지향적인 코드를 작성을 위해 노력하여,
  팀의 생산성을 높이고 싶습니다.

- .로그파일 관리의 중요성을 한번 더 느끼게 되었습니다.
  - 개발을 할 때 우리 조원들이 어떤예외가 발생했고,무슨 예외가 가장많이 발생했는지등을 로그
  관련 설정을 미리 세팅해서 기록해놨으면 더 좋았을거라고 생각했습니다. 그리고 조원이 어떻게 그
  예외를 해결했는지 공유했다면 서로가 한층 더 성장했을거라고 생각합니다.
  - 서비스를 시작했을 때 유저가와서 무엇을 했고, 어떤 예외에 직면했으며,몇시간동안 있었는지등
  좀더 자세하게 그들어온 유저의 traffic을 조사해볼수있고 체계적인 로그를 기록할수있게 생각해보면
   좋았을거라고 생각합니다. 저희 조원들이 단순히 4명이서 동시접속하여 사이트를 이용했을 때, 엄청
  난 양의 로그가 올라왓고, 어떤 유저가 무엇을했는지 전혀 분간조차 힘들었습니다. 나중에 새로운
  프로젝트를한다면, 로그관련 부분도 고려해보는 프로젝트를 진행해보고싶습니다.

