# 🧩 공강을 백으로 채우다, 공백

공백은 **대학생들의 공강 시간을 특별하게 채우는 모임 플랫폼**입니다.  
원하는 모임을 만들고 신청하며, 멤버들과 소통할 수 있는 공간을 제공하여, 공강 시간을 **의미 있는 경험**으로 바꿔줍니다.

![gongbaek](https://github.com/user-attachments/assets/c8838b3f-9ee8-447c-854e-7778d0867e21)

---

## ✨ Contributors

## ✨ Contributors

| | ⭐️**리더** 김효준 [@khyojun](https://github.com/khyojun) | 이현진 [@2hyunjinn](https://github.com/2hyunjinn) |
| :--- | :--- | :--- |
| | <img src="https://github.com/user-attachments/assets/9b8bba10-1729-4f74-ae96-ad721bf1fa83" width="300"/> | <img src="https://github.com/user-attachments/assets/0b5dfc62-7f39-4890-bb61-bf1e9a6a14d8" width="300"/> |
| **역할** | 백엔드 개발 / 인프라 담당 | 백엔드 개발 / QA 주관 |
| **담당 API** | <ul>  <li><strong>🗂️ 유저 관련</strong>  <ul>  <li>학교 검색 API</li>  <li>학과 검색 API</li>  <li>닉네임 검증 API</li>  <li>회원가입 API</li>  <li>소개글 검증 API</li>  <li>accessToken 재발급 API</li>  </ul>  </li>  <li><strong>📅 시간표 관련</strong>  <ul>  <li>내 강의 시간표 조회 API</li>  </ul>  </li>  <li><strong>🧩 모임 관련</strong>  <ul>  <li>모임 등록하기 API</li>  <li>모임 참여 멤버 전체 조회 API</li>  </ul>  </li>  <li><strong>💬 댓글 관련</strong>  <ul>  <li>댓글 작성 API</li>  <li>댓글 조회 API (모임방 / 채우기)</li>  <li>댓글 삭제 API</li>  </ul>  </li>  </ul> | <ul>  <li><strong>🗂️ 유저 관련</strong>  <ul>  <li>소셜 로그인(APPLE, KAKAO) API</li>  <li>로그아웃 API</li>  <li>회원탈퇴 API</li>  <li>학교 이메일 인증 코드 전송 API</li>  <li>학교 이메일 인증 코드 인증 API</li>  <li>유저 프로필 조회 API (학교명 / 닉네임)</li>  <li>나의 프로필 조회 API</li>  </ul>  </li>  <li><strong>🧩 모임 관련</strong>  <ul>  <li>나의 모임 전체 조회 API</li>  <li>참여 가능한 모임 조회 API</li>  <li>가장 가까운 모임 1개 조회 API</li>  <li>채우기 모임 전체 조회 API</li>  <li>모임 신청 API</li>  <li>모임 신청 취소하기 API</li>  <li>나의 모임 삭제하기 API</li>  <li>모임 상세 정보 조회 API</li>  <li>등록자 프로필 조회 API</li>  </ul>  </li></ul> |
| **기타 작업** | <ul><li>Public / Private Subnet 분리 작업</li><li>HTTPS 설정 및 도메인 연결</li><li>Docker 관련 설정 진행</li><li>무중단 배포를 위한 <strong>스크립트 작성</strong></li></ul> | <ul><li>서버 내부 QA 주관 및 보수 작업 진행</li><li>카카오 로그인 인증 플로우 구현</li>(RestClient 기반 사용자 정보 검증)</li><li>애플 로그인 인증 플로우 설계 및 토큰 파싱 구현</li>(idToken 디코딩 및 sub 추출 포함)</li></ul> |

---

## 🏗️ Architecture Overview

![Gongbaek_Server_Arch](https://github.com/user-attachments/assets/cee82b4e-3ae4-43e1-8f9b-14303d6eb8ad)

- **모놀리식 구조 기반**, 기능 단위 도메인 설계
- `Facade`, `Strategy`, `Registry` 패턴을 통해 **도메인 유연성 확보**
- 계층 분리 및 책임 분배로 **서비스 확장성과 유지보수성 강화**

---

## 🧩 주요 기능 요약

| 기능 | 설명 | 주요 특징 |
|------|------|-----------|
| **온보딩** | 유저 정보 및 시간표 입력 | 학교/학과 입력, 공강 시간 자동 도출 |
| **채우기** | 공강 시간에 맞는 모임 탐색 | 카테고리 필터링, 최신순 추천 |
| **모임 모집** | 직접 모임 개설 | 일회성/다회성, 장소·인원·소개글 설정 |
| **나의 채움** | 내가 만든/참여한 모임 관리 | 진행 상태 구분, 빠른 확인 |
| **스페이스** | 참여자 전용 커뮤니티 | 멤버 확인, 채팅 기능 |
| **모임 상세** | 신청/취소 및 댓글, 프로필 확인 | 권한 기반 기능 노출 (등록자 vs 참여자) |


---

## 🗂️ ERD

[🔗 ERD 자세히 보기](https://dbdiagram.io/d/gongbaek-67f3d76c4f7afba184a2ea24)

![gongbaek-erd](https://github.com/user-attachments/assets/53b28484-5337-4589-a0c7-a117b50f553c)

---

## 🚀 CI/CD 파이프라인

| 프로세스 | 설명 |
|----------|------|
| `gongbaek-build-ci` | 빌드 성공 여부 확인 |
| `gongbaek-test-ci` | 테스트 성공 여부 및 리포트 작성 |
| `gongbaek-cd` | EC2로 자동 배포 (무중단 배포 스크립트 적용) |

> GitHub Actions + EC2 + Docker 기반 자동화  
> 안정성과 유지보수를 고려한 **Blue-Green 배포 전략** 적용 예정

---

## ⚙️ 기술 스택

| 분야 | 스택 |
|------|------|
| IDE | IntelliJ |
| Language | Java 21 |
| Framework | Spring Boot 3.4.1, Gradle |
| ORM | Spring Data JPA |
| DB | MySQL |
| Auth | JWT |
| Infra | AWS EC2 / RDS / Nginx / Docker-Compose |
| CI/CD | GitHub Actions |
| Tools | Discord, Figma, Postman, Notion (API 명세) |

---

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

---

## 📋 Commit Convetion

- **init** : 프로젝트 초기 세팅 `[init] #1 프로젝트 초기 세팅`
- **docs** : README나 wiki 등의 문서 개정 `[docs] #14 리드미 수정`
- **feat** : 새로운 기능 구현 `[feat] #11 회원가입 API 기능 구현`
- **fix** : 코드 오류 수정 `[fix] #23 회원가입 비즈니스 로직 오류 수정`
- **refactor** : 내부 로직은 변경 하지 않고 기존의 코드를 개선하는 리팩터링 `[refactor] #15 클래스 분리`
- **chore** : 의존성 추가, yml 추가와 수정, 패키지 구조 변경, 파일 이동 등의 작업 `[chore] #30 파일명 변경`
- **test**: 테스트 코드 작성, 수정 `test: 로그인 API 테스트 코드 작성 (#20)`

--- 

## 📋 Git Convention

[공백 서버 팀의 Git 컨벤션이 궁금하다면? ✔️](https://sumptuous-viscose-f29.notion.site/Git-Convention-7ff513348d1f4ea1aeca732027ec8f12?pvs=4)

--- 

## 📋 Code Convention
<hr></hr>

[공백 서버 팀의 코드 컨벤션이 궁금하다면? ✔️](https://sumptuous-viscose-f29.notion.site/Code-Convention-f40b5a5de8fb497faeeac3e18768f973?pvs=4)

---

## 📁 폴더 구조

---

```
.
├── Application.java
├── api
│   ├── advertisement
│   │   └── controller
│   ├── auth
│   │   └── service
│   │       └── PlatformAuthService.java
│   ├── comment
│   │   ├── controller
│   │   │   └── CommentController.java
│   │   ├── dto
│   │   │   ├── DeleteCommentRequest.java
│   │   │   ├── ReadCommentRequest.java
│   │   │   ├── ReadCommentResponse.java
│   │   │   ├── WriteCommentEntityDto.java
│   │   │   ├── WriteCommentRequest.java
│   │   │   └── WriteCommentResponse.java
│   │   ├── facade
│   │   │   └── CommentFacade.java
│   │   ├── registry
│   │   │   ├── CommentStrategy.java
│   │   │   └── CommentStrategyRegistry.java
│   │   ├── service
│   │   │   └── CommentService.java
│   │   └── strategy
│   │       ├── EveryGroupCommentStrategy.java
│   │       └── OnceGroupCommentStrategy.java
│   ├── common
│   │   ├── ApiResponse.java
│   │   ├── HealthController.java
│   │   ├── ResponseBuilder.java
│   │   ├── ResponseError.java
│   │   └── ResponseSuccess.java
│   ├── email
│   │   └── service
│   │       ├── AuthCodeCacheService.java
│   │       ├── EmailProperties.java
│   │       └── MailService.java
│   ├── exception
│   │   ├── GlobalExceptionHandler.java
│   │   └── GongBaekException.java
│   ├── facade
│   │   ├── GroupRequestFacade.java
│   │   ├── LoginFacade.java
│   │   ├── MailFacade.java
│   │   ├── SearchSchoolFacade.java
│   │   ├── SearchSchoolMajorFacade.java
│   │   ├── SignUpFacade.java
│   │   ├── SignupRequestFacade.java
│   │   └── TimeTableFacade.java
│   ├── gongbaekTimeSlot
│   │   ├── controller
│   │   │   └── GongbaekTimeController.java
│   │   ├── dto
│   │   │   └── ReadInvalidTimeResponse.java
│   │   └── service
│   │       └── GongbaekTimeSlotService.java
│   ├── group
│   │   ├── ActiveCombinedGroupVoPreparer.java
│   │   ├── CombinedNearestGroupVo.java
│   │   ├── CombinedNearestGroupVoPreparer.java
│   │   ├── GroupStatusUpdater.java
│   │   ├── GroupVoAggregator.java
│   │   ├── controller
│   │   │   └── GroupController.java
│   │   ├── dto
│   │   │   ├── ActiveGroupsResponse.java
│   │   │   ├── CombinedGroupVos.java
│   │   │   ├── FillGroupFilterRequest.java
│   │   │   ├── FinalMyGroupResponse.java
│   │   │   ├── GroupRequest.java
│   │   │   ├── GroupResponse.java
│   │   │   ├── GroupUserInfoResponseDto.java
│   │   │   ├── LatestResponse.java
│   │   │   ├── MyGroupResponse.java
│   │   │   ├── NearestGroupResponse.java
│   │   │   ├── PrepareRegisterInfo.java
│   │   │   ├── ReadFillMembersRequest.java
│   │   │   ├── ReadFillMembersResponse.java
│   │   │   ├── RegisterGongbaekRequest.java
│   │   │   └── RegisterGroupResponse.java
│   │   ├── everyGroup
│   │   │   ├── controller
│   │   │   │   └── EveryGroupController.java
│   │   │   ├── service
│   │   │   │   └── EveryGroupService.java
│   │   │   └── strategy
│   │   │       ├── ApplyEveryGroupStrategy.java
│   │   │       ├── CancelEveryGroupStrategy.java
│   │   │       ├── DeleteEveryGroupStrategy.java
│   │   │       ├── EveryGroupInfoStrategy.java
│   │   │       ├── EveryGroupReadFillMemberStrategy.java
│   │   │       ├── EveryGroupUserInfoStrategy.java
│   │   │       ├── EveryLatestGroupStrategy.java
│   │   │       ├── NearestEveryGroupResponseStrategy.java
│   │   │       └── RegisterEveryGroupStrategy.java
│   │   ├── facade
│   │   │   ├── GroupFacade.java
│   │   │   └── PrepareRegisterGongbaekFacade.java
│   │   ├── onceGroup
│   │   │   ├── controller
│   │   │   │   └── OnceGroupController.java
│   │   │   ├── service
│   │   │   │   └── OnceGroupService.java
│   │   │   └── strategy
│   │   │       ├── ApplyOnceGroupStrategy.java
│   │   │       ├── CancelOnceGroupStrategy.java
│   │   │       ├── DeleteOnceGroupStrategy.java
│   │   │       ├── NearestOnceGroupResponseStrategy.java
│   │   │       ├── OnceGroupInfoStrategy.java
│   │   │       ├── OnceGroupReadFillMemberStrategy.java
│   │   │       ├── OnceGroupUserInfoStrategy.java
│   │   │       ├── OnceLatestGroupStrategy.java
│   │   │       └── RegisterOnceGroupStrategy.java
│   │   ├── registry
│   │   │   ├── ApplyGroupStrategy.java
│   │   │   ├── ApplyGroupStrategyRegistry.java
│   │   │   ├── CancelGroupStrategy.java
│   │   │   ├── CancelGroupStrategyRegistry.java
│   │   │   ├── DeleteGroupStrategy.java
│   │   │   ├── DeleteGroupStrategyRegistry.java
│   │   │   ├── GroupInfoStrategy.java
│   │   │   ├── GroupInfoStrategyRegistry.java
│   │   │   ├── GroupUserInfoStrategy.java
│   │   │   ├── GroupUserInfoStrategyRegistry.java
│   │   │   ├── LatestGroupStrategy.java
│   │   │   ├── LatestGroupStrategyRegistry.java
│   │   │   ├── MyGroupStrategy.java
│   │   │   ├── MyGroupStrategyRegistry.java
│   │   │   ├── NearestGroupResponseStrategy.java
│   │   │   ├── NearestGroupResponseStrategyRegistry.java
│   │   │   ├── ReadFillMemberStrategy.java
│   │   │   ├── ReadFillMemberStrategyRegistry.java
│   │   │   ├── RegisterGroupStrategy.java
│   │   │   └── RegisterGroupStrategyRegistry.java
│   │   ├── strategy
│   │   │   ├── MyApplyGroupStrategy.java
│   │   │   ├── MyRegisterGroupStrategy.java
│   │   │   ├── NearestBothGroupResponseStrategy.java
│   │   │   └── NearestEmptyResponseStrategy.java
│   │   └── validator
│   │       └── GroupValidator.java
│   ├── lectureTimeSlot
│   │   ├── controller
│   │   └── service
│   │       └── LectureTimeSlotService.java
│   ├── mapper
│   │   └── GroupResponseMapper.java
│   ├── school
│   │   ├── controller
│   │   │   └── SchoolController.java
│   │   ├── dto
│   │   │   └── SchoolSearchResponse.java
│   │   └── service
│   │       └── SchoolService.java
│   ├── schoolMajor
│   │   ├── controller
│   │   │   └── SchoolMajorController.java
│   │   ├── dto
│   │   │   └── SearchedSchoolMajorResponse.java
│   │   └── service
│   │       └── SchoolMajorService.java
│   ├── user
│   │   ├── NicknameValidator.java
│   │   ├── controller
│   │   │   └── UserController.java
│   │   ├── dto
│   │   │   ├── LoginRequest.java
│   │   │   ├── SignUpRequest.java
│   │   │   ├── SignUpResponse.java
│   │   │   ├── UserProfileResponse.java
│   │   │   ├── UserSchoolResponseDto.java
│   │   │   └── ValidIntroductionRequest.java
│   │   ├── facade
│   │   │   └── UserFacade.java
│   │   ├── service
│   │   │   └── UserService.java
│   │   └── vo
│   │       └── TimeTableVo.java
│   ├── userEveryGroup
│   │   └── service
│   │       └── UserEveryGroupService.java
│   └── userOnceGroup
│       └── service
│           └── UserOnceGroupService.java
├── domain
│   ├── BaseTimeEntity.java
│   ├── advertisement
│   │   ├── AdvertisementEntity.java
│   │   ├── application
│   │   └── infra
│   ├── comment
│   │   ├── CommentEntity.java
│   │   ├── application
│   │   │   └── CommentServiceImpl.java
│   │   └── infra
│   │       └── CommentRepository.java
│   ├── common
│   │   └── SameSchoolValidator.java
│   ├── constant
│   │   ├── Category.java
│   │   ├── FillGroupType.java
│   │   ├── Gender.java
│   │   ├── GroupType.java
│   │   ├── Mbti.java
│   │   ├── Platform.java
│   │   ├── Status.java
│   │   └── WeekDay.java
│   ├── group
│   │   ├── GroupCommentVoMaker.java
│   │   ├── GroupVoMaker.java
│   │   ├── IntroductionValidator.java
│   │   ├── LocationValidator.java
│   │   ├── TitleValidator.java
│   │   ├── dto
│   │   │   ├── GroupVo.java
│   │   │   ├── ReadEveryGroupMember.java
│   │   │   ├── ReadOnceGroupMember.java
│   │   │   └── RegisterGroupServiceRequest.java
│   │   ├── everyGroup
│   │   │   ├── EveryGroupEntity.java
│   │   │   ├── application
│   │   │   │   └── EveryGroupServiceImpl.java
│   │   │   ├── dto
│   │   │   │   ├── EveryGroupDto.java
│   │   │   │   ├── EveryGroupVo.java
│   │   │   │   └── ReadEveryGroup.java
│   │   │   ├── infra
│   │   │   │   └── EveryGroupRepository.java
│   │   │   └── vo
│   │   │       └── ReadEveryGroupCommentCommonVo.java
│   │   ├── onceGroup
│   │   │   ├── OnceGroupEntity.java
│   │   │   ├── application
│   │   │   │   └── OnceGroupServiceImpl.java
│   │   │   ├── dto
│   │   │   │   ├── OnceGroupDto.java
│   │   │   │   ├── OnceGroupVo.java
│   │   │   │   └── ReadOnceGroup.java
│   │   │   ├── infra
│   │   │   │   └── OnceGroupRepository.java
│   │   │   └── vo
│   │   │       └── ReadOnceGroupCommentCommonVo.java
│   │   └── vo
│   │       ├── GroupCommentVo.java
│   │       ├── NearestGroup.java
│   │       └── ReadCommentGroup.java
│   ├── school
│   │   ├── SchoolEntity.java
│   │   ├── application
│   │   │   ├── School.java
│   │   │   └── SchoolServiceImpl.java
│   │   ├── dto
│   │   │   └── SchoolSearchVo.java
│   │   └── infra
│   │       └── SchoolRepository.java
│   ├── schoolMajor
│   │   ├── SchoolMajorEntity.java
│   │   ├── application
│   │   │   ├── SchoolMajor.java
│   │   │   └── SchoolMajorServiceImpl.java
│   │   ├── dto
│   │   │   └── SearchSchoolMajorVo.java
│   │   └── infra
│   │       └── SchoolMajorRepository.java
│   ├── timslot
│   │   ├── ReadCommonInvalidTimeVoMaker.java
│   │   ├── gongbaekTimeSlot
│   │   │   ├── GongbaekTimeSlotEntity.java
│   │   │   ├── application
│   │   │   │   └── GongbaekTimeSlotServiceImpl.java
│   │   │   ├── dto
│   │   │   │   └── GongbaekTimeSlotRequest.java
│   │   │   └── infra
│   │   │       └── GongbaekTimeSlotRepository.java
│   │   ├── lectureTimeSlot
│   │   │   ├── LectureTimeSlotEntity.java
│   │   │   ├── application
│   │   │   │   └── LectureTimeSlotServiceImpl.java
│   │   │   ├── dto
│   │   │   │   └── LectureTimeSlotRequest.java
│   │   │   ├── infra
│   │   │   │   └── LectureTimeSlotRepository.java
│   │   │   └── vo
│   │   │       └── LectureTimeSlotVo.java
│   │   └── vo
│   │       └── ReadCommonInvalidTimeVo.java
│   ├── user
│   │   ├── UserEntity.java
│   │   ├── application
│   │   │   └── UserServiceImpl.java
│   │   ├── dto
│   │   │   ├── SaveUserSignUp.java
│   │   │   ├── UserInfo.java
│   │   │   ├── UserProfile.java
│   │   │   └── UserSchoolDto.java
│   │   └── infra
│   │       └── UserRepository.java
│   ├── userEveryGroup
│   │   ├── UserEveryGroupEntity.java
│   │   ├── application
│   │   │   └── UserEveryGroupServiceImpl.java
│   │   ├── dto
│   │   │   ├── FillMember.java
│   │   │   └── NearestEveryGroup.java
│   │   └── infra
│   │       └── UserEveryGroupRepository.java
│   └── userOnceGroup
│       ├── UserOnceGroupEntity.java
│       ├── application
│       │   └── UserOnceGroupServiceImpl.java
│       ├── dto
│       │   └── NearestOnceGroup.java
│       └── infra
│           └── UserOnceGroupRepository.java
├── global
│   ├── annotation
│   │   ├── Facade.java
│   │   ├── Registry.java
│   │   └── Strategy.java
│   ├── aop
│   │   └── LoggingAop.java
│   ├── config
│   ├── exception
│   ├── jwt
│   │   ├── JwtProperties.java
│   │   ├── JwtService.java
│   │   └── TokenVo.java
│   ├── schedule
│   │   ├── GroupUpdateScheduler.java
│   │   └── SchedulerConfig.java
│   └── util
│       ├── LengthValidator.java
│       ├── TimeConverter.java
│       └── TimeValidator.java
└── infra
    ├── Auth.java
    ├── oauth
    │   ├── AppleClientSecretGenerator.java
    │   ├── AppleOAuthClient.java
    │   ├── AppleProperties.java
    │   ├── KakaoOAuthClient.java
    │   ├── KakaoProperties.java
    │   └── OAuthClient.java
    ├── redis
    │   └── RedisService.java
    └── service
        ├── AppleLoginService.java
        ├── KakaoLoginService.java
        └── TokenParser.java

```

