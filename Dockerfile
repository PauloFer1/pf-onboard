FROM openjdk:8-jdk-alpine

LABEL maintainer="paulo.r.r.fernandes@gmail.com"

VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/pf-onboard.jar
ADD ${JAR_FILE} pf-onboard.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pf-onboard.jar"]