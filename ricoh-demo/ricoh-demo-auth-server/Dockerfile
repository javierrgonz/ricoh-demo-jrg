FROM openjdk:8-jdk-alpine
MAINTAINER javier.rg@protonmail.com
EXPOSE 8082
ADD target/ricoh-demo-auth-server.jar ricoh-demo-auth-server.jar
ENTRYPOINT ["java","-jar","ricoh-demo-auth-server.jar"]