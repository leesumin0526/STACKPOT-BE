

# STACKPOT

> **"팀 빌딩부터 프로젝트 운영까지, 개발 프로젝트의 전 과정을 관리하는 플랫폼"**

**🔗 Links**
- **서비스**: https://www.stackpot.co.kr
- **랜딩 페이지**: https://stackpot.qshop.ai/
- **GitHub**: https://github.com/STACKPOT/STACKPOT-BE

![image](https://github.com/user-attachments/assets/87d0dbcb-4a24-4877-8edb-dabe1050182f)

![image](https://github.com/user-attachments/assets/83374ba0-81b0-4ec5-9a9c-9c70f40240f4)

![image](https://github.com/user-attachments/assets/9e35b8fb-d198-41bf-b107-d8811aead7e0)

![image](https://github.com/user-attachments/assets/b0dae67e-d47c-4fab-a75b-a78b378d761c)

![image](https://github.com/user-attachments/assets/c149108e-2ccb-4957-ad64-7c75beba5ec6)

## 📋 프로젝트 소개

개발자 중심의 프로젝트 매칭 환경에서는 참여자의 기여도와 신뢰도를 객관적으로 판단할 수 있는 정보가 부족해 팀 빌딩이 주관적인 인상에 의존하는 문제가 있었습니다. 

Stackpot은 이를 해결하기 위해 **프로젝트 생성부터 종료까지의 전 과정을 하나의 흐름으로 기록·관리**하고, 역할별 캐릭터와 활동 기반 지표를 통해 진입 장벽을 낮추며 프로젝트 이력을 피드 형태로 축적해 **데이터 기반의 신뢰 판단이 가능한 매칭 환경**을 제공하는 플랫폼입니다.

## ✨ 주요 기능

| 기능 | 설명 |
|------|------|
| 📝 **AI 콘텐츠 요약** | OpenAI 기반 프로젝트 설명 및 진행 상황 자동 요약 |
| 🤖 **AI 닉네임 생성** | 창의적이고 일관된 톤의 닉네임 자동 생성 |
| 🌡️ **사용자 온도** | 신뢰도 기반 사용자 평가 시스템 |
| 📊 **마이페이지** | 참여 프로젝트 · 피드 · 배지 통합 관리 |
| 📱 **피드 시스템** | 시리즈 단위 개발 경험 공유 플랫폼 |
| 🔐 **소셜 로그인** | 카카오 · 구글 · 네이버 OAuth2 인증 |
| 👥 **프로젝트 매칭** | 개발자들의 팟(프로젝트) 생성 및 참여 |
| 🥕 **역할별 캐릭터** | BACKEND(브로콜리), FRONTEND(당근) 등 역할 기반 캐릭터 |
| 💬 **실시간 채팅** | WebSocket 기반 팟 내부 실시간 채팅 |
| 📋 **태스크 관리** | 프로젝트별 할 일 관리 및 진행률 추적 |
| 🏆 **배지 시스템** | 프로젝트 완료 및 성취에 따른 배지 획득 |
| 🔔 **알림 시스템** | 실시간 알림 + 이메일 알림 |

## 🛠 기술 스택

### Backend
- **Framework**: Spring Boot 3.3.6
- **Language**: Java 17
- **Database**: MySQL (AWS RDS), MongoDB, Redis
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security + JWT + OAuth2
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Gradle

### AI & External Services
- **AI**: OpenAI GPT-4 Turbo
- **File Storage**: AWS S3
- **Email**: Gmail SMTP

### Infrastructure & DevOps
- **Deployment**: Docker + AWS EC2 + Nginx
- **CI/CD**: GitHub Actions
- **Monitoring**: Spring Boot Actuator

### Additional Libraries
- **Real-time Communication**: WebSocket
- **Utilities**: Lombok, RestTemplate, WebFlux

## 🏗 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Load Balancer │    │   Backend       │
│   (React)       │◄──►│   (Nginx)       │◄──►│   (Spring Boot) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                       ┌─────────────────┐             │
                       │   File Storage  │◄────────────┤
                       │   (AWS S3)      │             │
                       └─────────────────┘             │
                                                        │
┌─────────────────┐    ┌─────────────────┐             │
│   Cache         │◄───│   Database      │◄────────────┘
│   (Redis)       │    │   (MySQL/RDS)   │
└─────────────────┘    └─────────────────┘
                                │
                       ┌─────────────────┐
                       │   NoSQL         │
                       │   (MongoDB)     │
                       └─────────────────┘
```

---

# 개발 가이드

## Commit Convention
- **[FEAT]** : 새로운 기능 구현
- **[MOD]** : 코드 수정 및 내부 파일 수정
- **[ADD]** : 부수적인 코드 추가 및 라이브러리 추가, 새로운 파일 생성
- **[CHORE]** : 버전 코드 수정, 패키지 구조 변경, 타입 및 변수명 변경 등의 작은 작업
- **[DEL]** : 쓸모없는 코드나 파일 삭제
- **[UI]** : UI 작업
- **[FIX]** : 버그 및 오류 해결
- **[HOTFIX]** : issue나 QA에서 문의된 급한 버그 및 오류 해결
- **[MERGE]** : 다른 브랜치와의 MERGE
- **[MOVE]** : 프로젝트 내 파일이나 코드의 이동
- **[RENAME]** : 파일 이름 변경
- **[REFACTOR]** : 전면 수정
- **[DOCS]** : README나 WIKI 등의 문서 개정

- ---

**📌 형식**:

- `[커밋 타입/#이슈번호] 커밋 내용`

**📌 예시**

- `[feat/#32] User 도메인 구현`
- `[feat/#32] User 필드값 annotation 추가`

## Branch Convention
1. **이슈 파기**
    
    **📌 형식**
    
    `[타입/#이슈번호] 이슈 내용`
    
    **📌 예시**
    
    - `[Feat/#11] User 도메인 구현`
    - `[Refactor/#2] User 관련 DTO 수정`
2. **브랜치 파기**
    
    **📌 형식**
    
    - `유형/#이슈번호-what`
    
    **📌 예시**
    
    - `feat/#11-login-view-ui`
1. **PR 올리기**
    
    **📌 형식**
    
    - `[유형] where / what`
    
    **📌 예시**
    
    - `[FEAT] 로그인 뷰 / UI 구현`
    
    **📌 PR Convention**
    
    ```
    ### PR 타입(하나 이상의 PR 타입을 선택해주세요)
    -[] 기능 추가
    -[] 기능 삭제
    -[] 버그 수정
    -[] 의존성, 환경 변수, 빌드 관련 코드 업데이트
    
    ### 반영 브랜치
    ex) feat/login -> dev
    
    ### 변경 사항
    ex) 로그인 시, 구글 소셜 로그인 기능을 추가했습니다.
    
    ### 테스트 결과
    ex) 베이스 브랜치에 포함되기 위한 코드는 모두 정상적으로 동작해야 합니다. 결과물에 대한 스크린샷, GIF, 혹은 라이브
    ```
## PR Convention
**📌 형식**

- `[유형/#이슈번호] where / what`

**📌 예시**

- `[FEAT/#3] 로그인 뷰 / UI 구현`

**📌 PR 프로세스**

1. **PR 생성**: 작업을 완료한 후, 변경 사항을 설명하는 PR을 생성합니다.
2. **코드 리뷰 요청**: PR이 생성되면 팀원들에게 코드 리뷰를 요청합니다.
3. **코드 리뷰 진행**: 리뷰어는 코드를 검토하고 피드백을 제공합니다.
4. **피드백 대응**: PR 작성자는 리뷰어의 피드백을 반영하여 코드를 수정합니다.
5. **리뷰어 동의**: 리뷰어는 수정된 코드를 다시 검토하고 동의합니다.
6. **PR 병합**: 필요한 승인 수가 충족되면, PR을 메인 브랜치에 병합합니다.

**📌 PR 템플릿**

```
### PR 타입(하나 이상의 PR 타입을 선택해주세요)
-[] 기능 추가
-[] 기능 삭제
-[] 버그 수정
-[] 의존성, 환경 변수, 빌드 관련 코드 업데이트

### 반영 브랜치
ex) feat/login -> dev

### 작업 내용
ex) 로그인 시, 구글 소셜 로그인 기능을 추가했습니다.

### 테스트 결과
ex) 베이스 브랜치에 포함되기 위한 코드는 모두 정상적으로 동작해야 합니다. 결과물에 대한 스크린샷, GIF, 혹은 라이브
```
