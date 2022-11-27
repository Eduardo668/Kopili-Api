FROM openjdk:17-jdk-alpine
COPY target/SA-BACKEND-0.0.1-SNAPSHOT.jar  KopiliApi.jar
ENTRYPOINT [ "java", "-jar", "/KopiliApi.jar" ]
EXPOSE 8080
