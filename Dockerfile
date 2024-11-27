# 1. 베이스 이미지 설정
FROM eclipse-temurin:17-jdk-alpine

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. 애플리케이션 JAR 복사
COPY target/Cartoon-for-Blind-Backend.jar app.jar

# 4. 컨테이너 시작 시 실행할 명령어 정의
ENTRYPOINT ["java", "-jar", "app.jar"]

# 5. (선택) 애플리케이션 실행 포트 설정
EXPOSE 8080
