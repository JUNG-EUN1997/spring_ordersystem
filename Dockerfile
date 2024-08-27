# 멀티 스테이지 빌드 방법 사용

# 첫번째 스테이지 진행 시작
FROM openjdk:11 as stage1

WORKDIR /app

# .은 WORKDIR인 /app에 생성된다는 의미
# /app/gradlew 파일로 생성
COPY gradlew .
# /app/gradle 폴더로 생성
COPY gradle gradle
COPY src src
COPY build.gradle .
COPY settings.gradle .

# 궝한이 없다면 아래 주석 해제
RUN chmod gradlew
RUN ./gradlew bootJar


# 두번째 스테이지 진행 시작
FROM openjdk:11 as stage2
WORKDIR /app
# stage1에 있는 jar를 stage2의 app.jar 라는 이름으로 copy
COPY --from=stage1 /app/build/libs/*.jar app.jar

#CMD 또는 ENTRYPOINT를 통해 컨테이너를 실행
ENTRYPOINT ["java", "-jar", "app.jar"]


# 실행하기
# 1. PS C:\study\devops\docker-practice\basic2\demo> 로 이동
# 2. docker run spring_test:latest
# 3. docker images 로 생성 잘 되었는지 검색
# 4. docker run -d  -p 8082:80 spring_test:latest

# 추가설명
# docker 컨테이너 내에서 밖의 전체 host를 지칭하는 도메인 : host.docker.internal
# docker run -d -p 8081:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/board spring_test:latest

# 볼륨컨테이너
# docker컨테이너 실행시에 볼륨을 설정할 때에는 -v 옵션 사용
# docker run -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/board -v 원하는폴더경로:/app/logs spring_test:latest
# 예시
# docker run -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/board -v C:\Users\rro06\OneDrive\Desktop\tmp_logs:/app/logs ordersystem:latest

# ===============================

# 1. 빌드 : docker build -t ordersystem:latest .
# 2. 컨테이너 실행
# docker run -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/ordersystem -v C:\Users\rro06\OneDrive\Desktop\tmp_logs:/app/logs ordersystem:latest


# ======== docker hub에 업로드 하기
# 1.docker hub에 올라가있는 이름명과 동일하게 build
## docker build -t rro0628/ordersystem:latest .
# 2. docker images로 알맞ㄱ ㅔ되었는지 확인
# 3. 로그인하기
## docker login
# 4. push 하기
## docker push rro0628/ordersystem:latest
# 5. 삭제하기 (삭제 전 실행중인 컨테이너 정지)
## docker rmi 이미지아이디
# 6. 다시 pull 받기
## docker pull rro0628/ordersystem:latest
# 7. 실행하기
## docker run -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/ordersystem -v C:\Users\rro06\OneDrive\Desktop\tmp_logs:/app/logs rro0628/ordersystem:latest
