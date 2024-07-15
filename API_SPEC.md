# API 스펙 문서

## 1. 회원 가입 API

- **경로 및 메서드**: POST /api/v1/members
- **설명**: 새로운 회원을 등록합니다.
- **요청 본문**:
  ```json
  {
    "name": "string",
    "email": "string",
    "phoneNumber": "string",
    "password": "string",
    "memberType": "STUDENT" or "INSTRUCTOR"
  }
  ```
- **응답**:
  - 성공 (200 OK):
    ```json
    {
      "id": "long",
      "name": "string",
      "email": "string",
      "phoneNumber": "string",
      "memberType": "STUDENT" or "INSTRUCTOR"
    }
    ```
  - 실패 (400 Bad Request): 이메일 중복 또는 유효성 검사 실패

## 2. 강의 등록 API

- **경로 및 메서드**: POST /api/v1/lectures
- **설명**: 새로운 강의를 등록합니다. (강사 회원만 가능)
- **요청 본문**:
  ```json
  {
    "name": "string",
    "maxStudents": "integer",
    "price": "decimal",
    "instructorId": "long"
  }
  ```
- **응답**:
  - 성공 (200 OK):
    ```json
    {
      "id": "long",
      "name": "string",
      "maxStudents": "integer",
      "price": "decimal",
      "instructorId": "long",
      "instructorName": "string"
    }
    ```
  - 실패 (400 Bad Request): 유효성 검사 실패
  - 실패 (404 Not Found): 강사를 찾을 수 없음

## 3. 강의 조회 API

### 3.1 모든 강의 조회

- **경로 및 메서드**: GET /api/v1/lectures
- **설명**: 모든 강의를 페이지네이션하여 조회합니다.
- **쿼리 파라미터**:
  - page: 페이지 번호 (기본값: 0)
  - size: 페이지 크기 (기본값: 20)
- **응답**:
  - 성공 (200 OK):
    ```json
    {
      "content": [
        {
          "id": "long",
          "name": "string",
          "maxStudents": "integer",
          "price": "decimal",
          "instructorId": "long",
          "instructorName": "string"
        }
      ],
      "pageable": {
        "pageNumber": "integer",
        "pageSize": "integer",
        "sort": {
          "empty": "boolean",
          "sorted": "boolean",
          "unsorted": "boolean"
        },
        "offset": "long",
        "paged": "boolean",
        "unpaged": "boolean"
      },
      "totalPages": "integer",
      "totalElements": "long",
      "last": "boolean",
      "size": "integer",
      "number": "integer",
      "sort": {
        "empty": "boolean",
        "sorted": "boolean",
        "unsorted": "boolean"
      },
      "numberOfElements": "integer",
      "first": "boolean",
      "empty": "boolean"
    }
    ```

### 3.2 수강생 수 기준 강의 조회

- **경로 및 메서드**: GET /api/v1/lectures/by-enrollment-count
- **설명**: 수강생 수가 많은 순서대로 강의를 조회합니다.
- **쿼리 파라미터**: 3.1과 동일
- **응답**: 3.1과 동일한 구조 (정렬 기준만 다름)

### 3.3 수강률 기준 강의 조회

- **경로 및 메서드**: GET /api/v1/lectures/by-enrollment-rate
- **설명**: 수강률(현재 수강생 수 / 최대 수강 인원)이 높은 순서대로 강의를 조회합니다.
- **쿼리 파라미터**: 3.1과 동일
- **응답**: 3.1과 동일한 구조 (정렬 기준만 다름)

## 4. 수강 신청 API

- **경로 및 메서드**: POST /api/v1/enrollments
- **설명**: 특정 강의에 수강 신청을 합니다.
- **요청 본문**:
  ```json
  {
    "memberId": "long",
    "lectureId": "long"
  }
  ```
- **응답**:
  - 성공 (200 OK):
    ```json
    {
      "id": "long",
      "memberId": "long",
      "memberName": "string",
      "lectureId": "long",
      "lectureName": "string",
      "enrollmentDate": "datetime"
    }
    ```
  - 실패 (400 Bad Request): 이미 수강 중이거나 정원 초과
  - 실패 (404 Not Found): 회원 또는 강의를 찾을 수 없음
