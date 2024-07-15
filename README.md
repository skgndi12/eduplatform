# 애플리케이션 실행 절차 문서

## 1. 사전 요구사항:
   - JDK 11 이상 설치
   - Maven 설치 (또는 프로젝트에 포함된 Maven Wrapper 사용)

## 2. 프로젝트 다운로드
   ```
   git clone https://github.com/skgndi12/eduplatform.git
   cd eduplatform
   ```

## 3. 테스트 실행
   ```
   ./mvnw test
   ```

## 4. 애플리케이션 빌드
   ```
   ./mvnw clean package
   ```
   - 이 명령어는 프로젝트를 빌드하고 실행 가능한 JAR 파일을 생성합니다.

## 5. 애플리케이션 실행
   ```
   java -jar target/eduplatform-0.0.1-SNAPSHOT.jar
   ```
   - 이 명령어로 애플리케이션을 실행합니다.
   - 기본적으로 애플리케이션은 8080 포트에서 실행됩니다.

## 6. API 테스트
   - 애플리케이션이 실행되면 Swagger UI를 통해 API를 테스트할 수 있습니다: http://localhost:8080/swagger-ui.html
   - 또는 curl, Postman 등의 도구를 사용하여 API를 직접 호출할 수 있습니다.

## 7. 데이터베이스
   - H2 인메모리 데이터베이스를 사용하고 있습니다.

## 8. 애플리케이션 종료
   - 콘솔에서 Ctrl+C를 누르면 애플리케이션이 종료됩니다.
