# **GANU**


**<2019 SM 통합성과대회 스터디상생플러스 장려상 수상작>**

**SK NUGU 스피커용 외출 도움 서비스 어플리케이션으로, 사용자가 간단한 발화를 통해 외출 시 체크해야할 항목과 날씨 정보에 대해 얻을 수 있도록 합니다.**
<p align="center"><img src="https://user-images.githubusercontent.com/46772883/111263434-38595a00-8669-11eb-84b7-05dda3f0248b.png" width="30%" height="30%"/></p>

<br>

### **주요 파일 설명**
+ **"src/main/java/com/example/ganu/controller"**
  + NUGU PlayBuilder를 통해 설정된 사용자 발화에 대해 날씨 정보를 응답하기 위한 소스 파일들입니다. 
  + 사용자의 응답 순서에 따라 처리하기 위해 Yes, ReYes 파일로 나누었고, 내용은 동일합니다
  + Spring Boot를 통해 서버를 구성하고, 기상청 RSS 정보를 파싱하여 playBuilder 측에 응답합니다.
+ **"GANU/src/main/java/com/example/ganu/domain/"**
  + 응답 형식을 설정하는 소스 파일들입니다.


<br>

## GANU 구현 과정

<p align="center"><img src="https://user-images.githubusercontent.com/46772883/111265119-f7167980-866b-11eb-935e-8a9683d4ee48.png" width="75%" height="75%"/></p>
<br>

### 사용자 발화, 스피커 응답, 연결 서버 설정 (in NUGU PlayBuilder)
1. 사용자 발화 및 스피커 응답 설정
  + 하나의 의사표현에 대한 intent를 만들고 이에 대한 예상 발화들을 작성한 뒤, 해당 발화를 인지했을 경우에 대한 스피커 응답 또한 작성합니다.
    + 어플리케이션을 실행시키는 사용자의 첫 발화는 '외출을 의미하는 것' (ex. "갔다 올게", "나 간다" 등)입니다.
    + 이에 대한 응답은 '외출 시 체크해야 할 항목'(ex. 가스벨브, 보일러, 전등)을 확인했는지 질문하는 것입니다.
        + 질문에 대한 사용자의 응답이 긍정일 경우, 날씨 정보를 담은 응답을 하고 대화를 마무리합니다.
        + 질문에 대한 사용자의 응답이 부정일 경우, 재확인 후 알려달라는 응답을 하고, 사용자의 재발화를 기다립니다. 이후 재확인 했다는 발화가 전달되면, 
        날씨 정보를 담은 응답을 하고 대화를 마무리합니다.
  + 사용자 발화와 응답 형식은은 아래 예시 그림과 같습니다.
<p align="center"><img src="https://user-images.githubusercontent.com/46772883/111271546-ae16f300-8674-11eb-8b16-a5c2ebbe582e.png" width="60%" height="60%"/></p>


2. 연결 서버 설정
  + 날씨 정보를 응답하기 위해 기상청 RSS의 정보를 파싱하는 프록시 서버와 연결합니다.
        
        
### 날씨 정보를 파싱한 뒤, PlayBuilder측으로 반환하기 위한 서버 구축 및 제어
1. AWS의 EC2 서버를 구축하고, Putty를 통해 제어합니다.
2. FileZilla를 통해 구현이 완료된 날씨 정보를 파싱하는 기능을 업로드합니다.


### 기상청 RSS 정보 파싱
1. 서울시 명동 위치를 기준으로 기상청 RSS 파일에서 날짜 및 날씨 정보를 파싱합니다.
2. 파싱한 정보를 JSON 형식으로 매핑하며, Spring boot를 통해 이를 RestAPI 및 POST 방식으로 접근 및 반환하도록 합니다.
