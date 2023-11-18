FROM openjdk:latest
ADD ../build/libs/media-service-0.0.2.jar spring-project-microservices.jar
ENTRYPOINT ["java", "-jar","spring-project-microservices.jar"]
EXPOSE 8080