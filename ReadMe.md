## 소개
Book Management System은 도서와 저자 정보를 관리하기 위한 간단한 RESTful 웹 애플리케이션입니다.  
Spring Boot로 구축되었으며, Swagger를 통해 API 명세를 제공합니다.

---

## 설치 및 실행 방법

### 1. 실행 환경
- JDK 17
- Spring Boot 3.4.3 
- Gradle
- Database H2

---

### 2. 설치

1. 프로젝트 클론
   ```bash
   git clone https://github.com/sskkilm/book-management-system.git
   
   cd book-management-system
   ```

2. 빌드
   ```bash
   ./gradlew build
   ```

3. application.yml 설정
   
    `src/main/resources/application.yml` 파일을 열고 데이터베이스 설정을 업데이트합니다.

   기본 설정:
   ```
   spring:
    datasource:
      url: jdbc:h2:mem:test
      username: sa
      password:
      driver-class-name: org.h2.Driver
    
    jpa:
      hibernate:
        ddl-auto: create
      properties:
        hibernate:
          format_sql: true
      open-in-view: false
    
    h2:
      console:
        enabled: true
    
    logging:
      level:
        org.hibernate.SQL: debug
   ```
---

### **3. 실행**

프로젝트를 Spring Boot로 실행:

```bash
./gradlew bootRun
```

애플리케이션이 `http://localhost:8080`에서 실행됩니다.

---


## Swagger 문서 접근 방법

API 명세는 Swagger를 통해 확인할 수 있습니다.

### 1. Swagger UI
- Swagger UI는 애플리케이션 실행 후 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)에서 접근 가능합니다.

### 2. OpenAPI JSON 명세
- JSON 명세는 기본적으로 `/v3/api-docs` 경로에서 제공됩니다.
  ```
  http://localhost:8080/v3/api-docs
  ```

---

## API 사용 예시

### 저자 API

#### 1. 저자 생성
새로운 저자를 생성합니다.

Endpoint: `POST /authors`  
Request Body:
```json
{
  "name": "홍길동",
  "email": "domain@example.com"
}
```

Response:
```json
{
  "code": "SUCCESS",
  "message": "리소스가 생성되었습니다.",
  "data": {
    "id": 1,
    "name": "홍길동",
    "email": "domain@example.com"
  }
}
```
---

#### 2. 저자 목록 조회
저자 목록을 조회합니다.

Endpoint: `GET /authors`

Response:
```json
{
  "code": "SUCCESS",
  "message": null,
  "data": {
    "authors": [
      {
        "id": 1,
        "name": "홍길동"
      }
    ]
  }
}
```
---
#### 3. 저자 상세 조회
특정 저자의 상세 정보를 조회합니다.

Endpoint: `GET /authors/{id}`  
Path Parameter:
- `id`: 저자의 고유 `id`

Response:
```json
{
  "code": "SUCCESS",
  "message": null,
  "data": {
    "id": 1,
    "name": "홍길동",
    "email": "domain@example.com"
  }
}
```
---
#### 4. 저자 수정
특정 저자의 정보를 수정합니다.(이전 데이터를 덮어쓰며, 모든 필드를 필수로 입력해야 합니다.)

Endpoint: `PUT /authors/{id}`  
Request Body:
```json
{
  "name": "수정하려는 저자 이름",
  "email": "수정하려는 저자 이메일"
}
```
Path Parameter:
- `id`: 저자의 고유 `id`

Response: 204 No Content

---
#### 5. 저자 삭제
특정 저자를 삭제합니다. (연관 도서도 모두 삭제합니다.)

Endpoint: `DELETE /authors/{id}`  
Path Parameter:
- `id`: 저자의 고유 `id`

Response: 204 No Content

---

### 도서 API

#### **1. 도서 생성**
새로운 도서를 생성합니다.

Endpoint: `POST /books`  
Request Body:
```json
{
  "title": "오브젝트",
  "description": "코드로 이해하는 객체지향 설계",
  "isbn": "1234567890",
  "publication_date": "2023-11-28",
  "authorId": 1
}
```
Response:
```json
{
  "code": "SUCCESS",
  "message": "리소스가 생성되었습니다.",
  "data": {
    "id": 1,
    "title": "오브젝트",
    "description": "코드로 이해하는 객체지향 설계",
    "isbn": "1234567890",
    "publication_date": "2023-11-28"
  }
}
```

---
#### 2. 도서 목록 조회
도서 목록을 조회합니다. (출판일 기준 필터링 및 페이징을 지원합니다.)

Endpoint: `GET /books`  
Query Parameters:
- `publication_date` (선택): `String` (YYYY-MM-DD 형식)
- `page` (선택): 페이지 번호 (기본값: 1)
- `size` (선택): 페이지 크기 (기본값: 10)

Response:
```json
{
  "code": "SUCCESS",
  "message": null,
  "data": {
    "contents": [
      {
        "id": 1,
        "title": "오브젝트",
        "isbn": "1234567890",
        "publication_date": "2025-02-25"
      }
    ],
    "page": 1,
    "size": 10,
    "total_pages": 1,
    "total_elements": 1
  }
}
```
---
#### 3. 도서 상세 조회
특정 도서의 상세 정보를 조회합니다.

Endpoint: `GET /books/{id}`  
Path Parameter:
- `id`: 도서의 고유 `id`

Response:
```json
{
  "code": "SUCCESS",
  "message": null,
  "data": {
    "id": 1,
    "title": "오브젝트",
    "description": "코드로 이해하는 객체지향 설계",
    "isbn": "1234567890",
    "publication_date": "2025-02-25",
    "author": {
      "id": 1,
      "name": "홍길동",
      "email": "domain@example.com"
    }
  }
}
```
---
#### 4. 도서 수정
특정 도서의 정보를 수정합니다. (이전 데이터를 덮어쓰며, 모든 필드를 필수로 입력해야 합니다.)

Endpoint: `PUT /books/{id}`  
Request Body:
```json
{
  "title": "오브젝트",
  "description": "코드로 이해하는 객체지향 설계",
  "isbn": "1234567890",
  "publication_date": "2025-02-25"
}
```
Path Parameter:
- `id`: 도서의 고유 `id`

Response: 204 No Content

---

#### 5. 도서 삭제
특정 도서를 삭제합니다.

Endpoint: `DELETE /books/{id}`  
Path Parameter:
- `id`: 도서의 고유 `id`

Response: 204 No Content

---

## 에러 응답
모든 에러 응답은 아래와 같은 공통 형식을 따릅니다:

Response Example:
```json
{
  "code": "에러 코드",
  "message": "에러 메세지입니다.",
  "detail": "실제 발생한 에러의 세부 정보가 여기에 포함됩니다."
}
```

