#사용할 base 이미지 선택
# 내꺼 ec2 아키텍처는 arm64v8
#FROM : 도커 이미지 생성의 기초가 되는 이미지 지정
FROM arm64v8/eclipse-temurin:17-jdk-focal

#build/libs/ 에 있는 jar 파일을 JAR_FILE 변수에 저장
#ARG : 변수 선언
ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 app.jar로 복사
# COPY: 해당 이미지 안에 특정 파일을 미리 넣어두는 역할
COPY ${JAR_FILE} app.jar

# Docker 컨테이너가 시작될 때 /app.jar 실행
#애플리케이션 타임존을 대한민국 서울로 설정
#ENTRYPOINT : 컨테이너가 생성되고 최초로 실행할 때 항상 수행되는 명령어 지정
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]