FROM openjdk:17-alpine

WORKDIR /opt/server-project
COPY ./target/server-project-0.0.1-SNAPSHOT.jar server-project.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "server-project.jar"]