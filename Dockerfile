FROM openjdk:11-jre-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=stg", "./app.jar"]