FROM openjdk:11-jre-slim
COPY --from=build /usr/app/target/*.jar /usr/app/app.jar
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]