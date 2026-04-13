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

<img width="1069" height="478" alt="image" src="https://github.com/user-attachments/assets/3fbadd1e-8e09-409c-b701-9ddc1cb598df" />


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

## 8. Git 작업 과정

```bash
git checkout -b feature/schedule-create
git add .
git commit -m "feat: 일정 생성 및 조회 API 구현, JSON 매핑 오류 수정"
git push -u origin feature/schedule-create
```

→ GitHub에서 Pull Request 생성 후 Merge

---

## 9. 회고

이번 프로젝트를 통해 단순한 CRUD 구현을 넘어
DB 연결 오류, JSON 매핑 문제, null 제약 조건 오류 등을 직접 해결하며
백엔드 개발 흐름을 이해할 수 있었습니다.

또한 Git 브랜치 전략과 Pull Request 과정을 경험하며
협업 기반 개발 방식에 대해 학습할 수 있었습니다.
