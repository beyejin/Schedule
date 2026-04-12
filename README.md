# 📅 Schedule API Project

## 1. 프로젝트 소개

Spring Boot와 MySQL을 활용하여 일정(Schedule)을 관리하는 REST API 프로젝트입니다.
사용자는 일정을 생성하고, 조회(전체/단건/작성자 기준), 수정, 삭제할 수 있습니다.

본 프로젝트는 계층형 구조(Controller → Service → Repository)를 기반으로 구현되었으며,
DTO를 활용하여 요청/응답을 분리하고 RESTful API 설계를 적용하였습니다.

---

## 2. 개발 환경

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Gradle
* IntelliJ IDEA
* Postman

---

## 3. 프로젝트 구조

```bash
src
├── main
│   ├── java
│   │   └── com.example.schedule
│   │       ├── controller
│   │       │   └── ScheduleController.java
│   │       ├── dto
│   │       │   ├── CreateScheduleRequest.java
│   │       │   ├── CreateScheduleResponse.java
│   │       │   ├── DeleteScheduleRequest.java
│   │       │   ├── GetScheduleResponse.java
│   │       │   ├── UpdateScheduleRequest.java
│   │       │   └── UpdateScheduleResponse.java
│   │       ├── entity
│   │       │   └── Schedule.java
│   │       ├── repository
│   │       │   └── ScheduleRepository.java
│   │       ├── service
│   │       │   └── ScheduleService.java
│   │       └── ScheduleApplication.java
│   └── resources
│       └── application.properties
```

---

## 4. ERD

```text
Schedule
-------------------------
id         BIGINT PK
title      VARCHAR
content    VARCHAR
author     VARCHAR
password   VARCHAR
createdAt  DATETIME
updatedAt  DATETIME
```

---

## 5. API 명세

### 5-1. 일정 생성

* **Method**: `POST`
* **URL**: `/schedules`

#### Request

```json
{
  "title": "과제 제출",
  "content": "Spring 과제 제출하기",
  "author": "한예진",
  "password": "1234"
}
```

#### Response

* **Status**: `201 Created`

```json
{
  "id": 1,
  "title": "과제 제출",
  "content": "Spring 과제 제출하기",
  "author": "한예진",
  "createdAt": "2026-04-12T20:20:00",
  "updatedAt": "2026-04-12T20:20:00"
}
```

---

### 5-2. 전체 일정 조회

* **Method**: `GET`
* **URL**: `/schedules`

---

### 5-3. 작성자 기준 조회

* **Method**: `GET`
* **URL**: `/schedules?author=한예진`

---

### 5-4. 단건 조회

* **Method**: `GET`
* **URL**: `/schedules/{id}`

---

### 5-5. 일정 수정

* **Method**: `PUT`
* **URL**: `/schedules/{id}`

#### Request

```json
{
  "title": "수정된 제목",
  "author": "한예진",
  "password": "1234"
}
```

👉 비밀번호 검증 후 수정

---

### 5-6. 일정 삭제

* **Method**: `DELETE`
* **URL**: `/schedules/{id}`

#### Request

```json
{
  "password": "1234"
}
```

👉 비밀번호 일치 시 삭제

---

## 6. 주요 기능

* 일정 생성(Create)
* 전체 일정 조회(Read)
* 단건 조회(Read)
* 작성자(author) 기준 조회 (optional 파라미터)
* 수정일(updatedAt) 기준 정렬
* 일정 수정(Update)
* 비밀번호 검증 기반 삭제(Delete)

---

## 7. 실행 방법

### 7-1. DB 생성

```sql
CREATE DATABASE schedule;
```

### 7-2. application.properties 설정

```properties
spring.application.name=schedule

spring.datasource.url=jdbc:mysql://localhost:3306/schedule
spring.datasource.username=root
spring.datasource.password=비밀번호
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

### 7-3. 실행

`ScheduleApplication` 실행

정상 실행 시:

```text
Tomcat started on port 8080
Started ScheduleApplication
```

---

## 8. 트러블슈팅

### 8-1. Unknown database `schedule`

#### 문제

애플리케이션 실행 시 아래와 같은 오류가 발생했다.

```text
Unknown database 'schedule'
```

#### 원인

`application.properties`의 DB URL은 `schedule` 데이터베이스를 바라보고 있었지만,
MySQL에 해당 데이터베이스가 생성되어 있지 않았다.

#### 해결

MySQL에서 아래 명령어를 실행하여 데이터베이스를 생성하였다.

```sql
CREATE DATABASE schedule;
```

이후 `spring.datasource.url=jdbc:mysql://localhost:3306/schedule` 설정과 DB 이름을 일치시켜 해결하였다.

---

### 8-2. JSON parse error

#### 문제

POST 요청 시 아래와 같은 오류가 발생했다.

```text
JSON parse error: Unexpected character ... was expecting comma to separate Object entries
```

#### 원인

Postman에서 요청 Body를 작성할 때 JSON 문법이 올바르지 않았다.
특히 필드 사이 쉼표가 누락된 경우 파싱 오류가 발생했다.

#### 해결

JSON 형식을 다시 확인하고 올바른 문법으로 요청을 보냈다.

```json
{
  "title": "테스트",
  "content": "내용",
  "author": "한예진"
}
```

이를 통해 API 요청 데이터는 값 자체뿐 아니라 JSON 형식도 정확해야 한다는 점을 확인할 수 있었다.

---

### 8-3. `content` null 에러

#### 문제

일정 생성 요청 시 아래와 같은 오류가 발생했다.

```text
not-null property references a null or transient value for entity Schedule.content
```

#### 원인

요청 JSON에서 필드명을 `content`가 아니라 `contents`로 잘못 작성하여,
Spring이 DTO에 값을 정상적으로 바인딩하지 못했다.
그 결과 `content` 값이 `null`로 들어가면서 DB 저장 시 not-null 제약 조건에 걸렸다.

#### 해결

JSON 요청 필드명을 DTO와 동일하게 수정하였다.

```json
{
  "title": "멍청이",
  "content": "안녕하세요",
  "author": "한예진"
}
```

이 과정을 통해 요청 JSON의 key 이름은 DTO 필드명과 정확히 일치해야 한다는 점을 배웠다.

---

### 8-4. 포트 문제로 착각한 실행 오류

#### 문제

초기에는 애플리케이션 실행 실패 메시지를 보고 포트 충돌 문제라고 판단했다.

#### 원인

실제로는 포트 충돌이 아니라 ApplicationContext 초기화 과정에서 DB 연결 실패가 발생한 것이었다.
포트 충돌이라면 보통 `Port 8080 was already in use` 같은 메시지가 나타나야 하지만,
이번 경우는 DB 연결 오류가 근본 원인이었다.

#### 해결

에러 로그의 핵심 원인을 다시 확인했고, `Caused by` 아래의 실제 예외 메시지를 기준으로 원인을 추적하였다.
그 결과 포트 문제가 아니라 DB 미생성 문제임을 확인하고 데이터베이스 생성으로 해결하였다.

이 경험을 통해 스프링 실행 오류는 마지막 줄보다 `Caused by` 로그를 먼저 보는 습관이 중요하다는 점을 배웠다.

---

### 8-5. MySQL Dialect 설정 관련 경고

#### 문제

실행 중 아래와 같은 경고 메시지가 출력되었다.

```text
MySQLDialect does not need to be specified explicitly
```

#### 원인

Hibernate가 MySQL Dialect를 자동으로 감지할 수 있는데도,
`application.properties`에 `spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect`를 직접 명시하고 있었다.

#### 해결

해당 설정은 필수가 아니므로 제거 가능하다는 점을 확인하였다.
프로젝트 실행에는 큰 문제가 없었지만, 불필요한 설정을 줄이는 방향으로 정리할 수 있었다.

이 과정을 통해 프레임워크가 자동으로 처리하는 설정과 직접 지정해야 하는 설정을 구분하는 것이 중요하다는 점을 배웠다.

---

### 8-6. Git 작업 후 PR이 바로 보이지 않았던 문제

#### 문제

브랜치를 생성하고 push까지 완료했지만, GitHub 화면에서 Pull Request 버튼이 바로 보이지 않았다.

#### 원인

비교 화면에서 `base: main`, `compare: main`으로 설정되어 있었기 때문에
브랜치 간 차이를 GitHub가 표시하지 못하고 있었다.

#### 해결

비교 브랜치를 `feature/schedule-create`로 변경하여
`base: main`, `compare: feature/schedule-create` 상태로 맞춘 뒤 Pull Request를 생성할 수 있었다.

이 과정을 통해 PR은 단순히 push만으로 생성되는 것이 아니라,
어느 브랜치를 어느 브랜치로 병합할 것인지 비교 기준을 올바르게 설정해야 한다는 점을 이해했다.

---

### 8-7. main 브랜치에서 먼저 작업한 문제

#### 문제

처음에는 기능 개발을 별도 브랜치가 아니라 `main` 브랜치에서 진행했다.

#### 원인

브랜치 전략에 익숙하지 않아 작업 전에 feature 브랜치를 생성하지 못했다.

#### 해결

다행히 아직 커밋 전 상태였기 때문에, 현재 작업 내용을 유지한 채 아래 명령어로 새 브랜치를 생성하여 문제를 해결했다.

```bash
git checkout -b feature/schedule-create
```

그 후 해당 브랜치에서 add, commit, push를 진행하고 Pull Request까지 연결할 수 있었다.

이 경험을 통해 기능 개발은 main이 아니라 별도 feature 브랜치에서 시작하는 습관이 중요하다는 점을 배웠다.

---

### 8-8. 요청 데이터와 엔티티 제약 조건의 불일치

#### 문제

API 요청은 전송되었지만 DB 저장 과정에서 예외가 발생하였다.

#### 원인

엔티티에는 `nullable = false` 같은 제약 조건이 적용되어 있는데,
요청 데이터 검증이 충분히 이루어지지 않으면 런타임에서 DB 저장 단계에 가서야 오류가 발생하게 된다.

#### 해결

요청 DTO와 엔티티의 필수 필드를 다시 확인하고,
Postman 테스트 시 필수값이 모두 포함되도록 요청 형식을 점검하였다.

이 경험을 통해 API 개발에서는
요청 DTO, 서비스 로직, 엔티티 제약 조건이 서로 일관되게 맞물려야 한다는 점을 알게 되었다.

---

## 9. 학습한 내용

* Spring Boot 기반 REST API 설계
* Controller / Service / Repository 구조 이해
* DTO를 활용한 요청/응답 분리
* JPA를 통한 DB 연동
* MySQL 연결 및 오류 해결
* Postman을 통한 API 테스트
* Git 브랜치 및 Pull Request workflow 경험

---

## 10. Git 작업 과정

```bash
git checkout -b feature/schedule-create
git add .
git commit -m "feat: 일정 생성 및 조회 API 구현, JSON 매핑 오류 수정"
git push -u origin feature/schedule-create
```

→ GitHub에서 Pull Request 생성 후 Merge

---

## 11. 회고

이번 프로젝트를 통해 단순한 CRUD 구현을 넘어
DB 연결 오류, JSON 매핑 문제, null 제약 조건 오류 등을 직접 해결하며
백엔드 개발 흐름을 이해할 수 있었습니다.

또한 Git 브랜치 전략과 Pull Request 과정을 경험하며
협업 기반 개발 방식에 대해 학습할 수 있었습니다.
