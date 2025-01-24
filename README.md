# 공강을 백으로 채우다, 공백 🧩
공백은 대학생들의 공강 시간을 특별하게 채우는 모임 플랫폼입니다.<br>
원하는 모임을 만들고 신청하며, 멤버들과 소통할 수 있는 공간을 제공하여 공강 시간을 의미 있는 경험으로 바꿔줍니다.
<br>

![gongbaek](https://github.com/user-attachments/assets/c8838b3f-9ee8-447c-854e-7778d0867e21)
<br/>

<br>

## ✨ Contributors

---

| |                                                                                                        ⭐️**리더** 김효준 [@khyojun](https://github.com/khyojun)                                                                                                        |                                                                                                                             이현진 [@2hyunjinn](https://github.com/2hyunjinn)                                                                                                                             |
| :---: |:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| |                                                                                      ![효준](https://github.com/user-attachments/assets/9b8bba10-1729-4f74-ae96-ad721bf1fa83)                                                                                       |                                                                                                         ![현진](https://github.com/user-attachments/assets/0b5dfc62-7f39-4890-bb61-bf1e9a6a14d8)                                                                                                         |
| **역할** |                                                                                                                          백엔드 개발 / 인프라 담당                                                                                                                          |                                                                                                                                             백엔드 개발 / QA 주관                                                                                                                                             |
| **담당 API** | <ul><li>내 강의 시간표 조회 API</li><li>모임 등록하기 API</li><li>모임 참여 멤버 전체 조회 API</li><li>댓글 작성 API</li><li>댓글 조회 API (모임방 / 채우기)</li><li>댓글 삭제 API</li><li>학교 검색 API</li><li>학과 검색 API</li><li>닉네임 검증 API</li><li>소개글 검증 API</li><li>회원가입 API</li><li>JWT 재발급 API</li></ul> | <ul><li>나의 모임 전체 조회 API</li><li>참여 가능한 모임 조회 API (최신순 5개)</li><li>가장 가까운 모임 1개 조회 API</li><li>채우기 모임 전체 조회 API</li><li>모임 신청 API</li><li>모임 신청 취소하기 API</li><li>나의 모임 삭제하기 API</li><li>등록자 프로필 조회 API</li><li>모임 상세 정보 조회 API</li><li>유저 프로필 조회 API (학교명 / 닉네임)</li><li>광고 배너 전체 조회 API (5개)</li></ul> |
| **기타 작업** |                                                                <ul><li>Public / Private Subnet 분리 작업</li><li>HTTPS 설정 및 도메인 연결   </li><li>Docker 관련 설정 진행</li><li>무중단 배포를 위한 **스크립트 작성**</li></ul>                                                                |                                                                                                                               <ul><li>서버 내부 QA 주관 및 보수 작업 진행 </li></ul>                                                                                                                                |

<br>

## Architecture v1

---
![Gongbaek_Server_Arch](https://github.com/user-attachments/assets/cee82b4e-3ae4-43e1-8f9b-14303d6eb8ad)

<br>

<h2>📌 주요 기능</h2>
---

| **기능**          | **설명**                                                                                                                                       | **주요 특징**                                                                                                  |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| **1️⃣ 온보딩**     | 나의 정보를 입력하고 공강 시간을 채울 준비를 할 수 있는 공간                                                                                       | - 학교와 학과 등 간단한 정보를 입력<br>- 수업 시간표를 바탕으로 공강 시간표 확인                                                                   |
| **2️⃣ 채우기**     | 나의 공강 시간에 열리는 모임을 확인할 수 있는 공간                                                                                                 | - 카테고리 필터링<br>- ~~요일별 필터링 (추후 구현 예정)~~<br>- ~~일회성 / 다회성 모임 필터링 (추후 구현 예정)~~<br>- 원하는 모임 신청 가능             |
| **3️⃣ 모임 모집**  | 나의 공강 시간에 하고 싶은 모임을 직접 등록할 수 있는 공간                                                                                        | - 주기 선택 (일회성 / 다회성)<br>- 날짜와 시간<br>- 카테고리 / 커버사진<br>- 장소 / 인원 / 소개글 입력 후 등록                                       |
| **4️⃣ 나의 채움**  | 내가 참여하는 모임 현황을 한눈에 확인할 수 있는 공간                                                                                               | - **내가 모집한 모임**: 진행 중 / 종료된 모임 구분 가능<br>- **내가 신청한 모임**: 진행 중 / 종료된 모임 구분 가능                                |
| **5️⃣ 스페이스**   | 같은 모임을 신청한 멤버만 참여할 수 있는 공간                                                                                                     | - 참여 멤버 정보 확인<br>- 대화를 통해 모임 준비                                                                                             |
| **6️⃣ 모임 상세**  | 모임에 대한 다양한 정보를 확인하고 신청할 수 있는 공간                                                                                            | - 댓글 작성 및 조회<br>- 모임 신청 및 취소<br>- 등록자 프로필 조회<br>- 모임 시간, 날짜, 장소 등 모임 정보 조회<br>**⭐️등록자**: 멤버 관리 가능<br>**⭐️참여자**: 등록자와 대화 후 모임 신청 가능 |

<br/><br/>





## 🧾 ERD
<hr></hr>

![GONGBAEK-ERD](https://github.com/user-attachments/assets/6ddd368b-7231-4cec-9a54-479f31b485af)

[ERD](https://dbdiagram.io/d/gongbaek-677813005406798ef737c1cb)

<br>

## 📋 Branch Convention

- `release` : 프로덕트를 배포하는 브랜치입니다.
- `main` : 프로덕트 배포 전 기능을 개발하는 브랜치입니다.
- `feat` : 단위 기능을 개발하는 브랜치로 단위 기능 개발이 완료되면 main 브랜치에 merge 합니다.
- `fix` : 버그 수정
- `docs` :  문서 작업 진행
- `refactor` : 리팩토링
- `chore`: 위 3개 아닌것
- `init`  초기 설정 작업
- `deploy`: 배포작업 진행시


<br><br>
## 📋 Commit Convetion

- **init** : 프로젝트 초기 세팅 `[init] #1 프로젝트 초기 세팅`
- **docs** : README나 wiki 등의 문서 개정 `[docs] #14 리드미 수정`
- **feat** : 새로운 기능 구현 `[feat] #11 회원가입 API 기능 구현`
- **fix** : 코드 오류 수정 `[fix] #23 회원가입 비즈니스 로직 오류 수정`
- **refactor** : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩터링 `[refactor] #15 클래스 분리`
- **chore** : 의존성 추가, yml 추가와 수정, 패키지 구조 변경, 파일 이동 등의 작업 `[chore] #30 파일명 변경`
- **test**: 테스트 코드 작성, 수정 `test: 로그인 API 테스트 코드 작성 (#20)`

<br>



<br><br>
## 📋 Git Convention
<hr></hr>

[공백 서버 팀의 Git 컨벤션이 궁금하다면? ✔️](https://sumptuous-viscose-f29.notion.site/Git-Convention-7ff513348d1f4ea1aeca732027ec8f12?pvs=4)

<br>


## 📋 Code Convention
<hr></hr>

[공백 서버 팀의 코드 컨벤션이 궁금하다면? ✔️](https://sumptuous-viscose-f29.notion.site/Code-Convention-f40b5a5de8fb497faeeac3e18768f973?pvs=4)

<br>


## 📋 CI/CD
<hr></hr>

### 💡 gongbaek-build-ci
`build` 가 성공적으로 수행되는지 검사합니다.

### 💡 gongbaek-cd
자동적으로 배포가 되기 위한 과정입니다.

### 💡 gongbaek-test-ci
`test` 가 성공적으로 수행되는지 검사하고 보고서를 작성합니다.


<br>


<br>

## Teck Stack ✨
<hr></hr>

| IDE | IntelliJ |
|:---|:---|
| Language | Java 21 |
| Framework | Spring Boot 3.4.1, Gradle |
| Authentication | JSON Web Tokens |
| Orm | Spring Data JPA |
| Database | MySQL |
| External | AWS EC2, AWS RDS, Nginx, Docker, Docker-Compose |
| CI/CD | Github Action |
| API Docs | Notion |
| Other Tool | Discord, Postman, Figma |

<br>



## 📁 폴더 구조

---

```
gongbaek-api
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 com
│   │   │   │    └── 📁 ggang.be
│   │   │   │        ├── 📁 api
│   │   │   │        │   ├── 📁 advertisement
│   │   │   │        │   │   └── 📁 controller
│   │   │   │        │   ├── 📁 comment
│   │   │   │        │   │   ├── 📁 controller
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   ├── 📁 facade
│   │   │   │        │   │   ├── 📁 registry
│   │   │   │        │   │   ├── 📁 service
│   │   │   │        │   │   └── 📁 strategy
│   │   │   │        │   ├── 📁 common
│   │   │   │        │   ├── 📁 exception
│   │   │   │        │   ├── 📁 group
│   │   │   │        │   │   ├── 📁 everyGroup
│   │   │   │        │   │   │   ├── 📁 controller
│   │   │   │        │   │   │   ├── 📁 service
│   │   │   │        │   │   │   └── 📁 strategy
│   │   │   │        │   │   ├── 📁 onceGroup
│   │   │   │        │   │   │   ├── 📁 controller
│   │   │   │        │   │   │   ├── 📁 service
│   │   │   │        │   │   │   └── 📁 strategy
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   ├── 📁 facade
│   │   │   │        │   │   ├── 📁 registry
│   │   │   │        │   │   └── 📁 strategy
│   │   │   │        │   ├── 📁 gongbaekTimeSlot
│   │   │   │        │   │   ├── 📁 controller
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   └── 📁 service
│   │   │   │        │   ├── 📁 lectureTimeSlot
│   │   │   │        │   │   ├── 📁 controller
│   │   │   │        │   │   └── 📁 service
│   │   │   │        │   ├── 📁 school
│   │   │   │        │   │   ├── 📁 controller
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   └── 📁 service
│   │   │   │        │   ├── 📁 user
│   │   │   │        │   │   ├── 📁 controller
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   ├── 📁 service
│   │   │   │        │   │   └── 📁 vo
│   │   │   │        ├── 📁 domain
│   │   │   │        │   ├── 📁 advertisement
│   │   │   │        │   │   ├── 📁 application
│   │   │   │        │   │   └── 📁 infra
│   │   │   │        │   ├── 📁 comment
│   │   │   │        │   │   ├── 📁 application
│   │   │   │        │   │   └── 📁 infra
│   │   │   │        │   ├── 📁 common
│   │   │   │        │   ├── 📁 group
│   │   │   │        │   │   ├── 📁 everyGroup
│   │   │   │        │   │   │   ├── 📁 application
│   │   │   │        │   │   │   ├── 📁 dto
│   │   │   │        │   │   │   ├── 📁 infra
│   │   │   │        │   │   │   └── 📁 vo
│   │   │   │        │   │   ├── 📁 onceGroup
│   │   │   │        │   │   │   ├── 📁 application
│   │   │   │        │   │   │   ├── 📁 dto
│   │   │   │        │   │   │   ├── 📁 infra
│   │   │   │        │   │   │   └── 📁 vo
│   │   │   │        │   │   └── 📁 vo
│   │   │   │        │   ├── 📁 school
│   │   │   │        │   │   ├── 📁 application
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   └── 📁 infra
│   │   │   │        │   ├── 📁 timslot
│   │   │   │        │   │   ├── 📁 gongbaekTimeSlot
│   │   │   │        │   │   │   ├── 📁 application
│   │   │   │        │   │   │   ├── 📁 dto
│   │   │   │        │   │   │   └── 📁 infra
│   │   │   │        │   │   ├── 📁 lectureTimeSlot
│   │   │   │        │   │   │   ├── 📁 application
│   │   │   │        │   │   │   ├── 📁 dto
│   │   │   │        │   │   │   ├── 📁 infra
│   │   │   │        │   │   │   └── 📁 vo
│   │   │   │        │   │   └── 📁 vo
│   │   │   │        │   ├── 📁 user
│   │   │   │        │   │   ├── 📁 application
│   │   │   │        │   │   ├── 📁 dto
│   │   │   │        │   │   └── 📁 infra
│   │   │   │        ├── 📁 global
│   │   │   │        │   ├── 📁 annotation
│   │   │   │        │   ├── 📁 aop
│   │   │   │        │   ├── 📁 config
│   │   │   │        │   ├── 📁 exception
│   │   │   │        │   ├── 📁 jwt
│   │   │   │        │   ├── 📁 schedule
│   │   │   │        │   └── 📁 util
│   │   │   │        ├── 📁 config
│   │   │   │        └── Application.java
│   │   │   └── 📁 resources
│   └── 📁 test

```

<br><br>
