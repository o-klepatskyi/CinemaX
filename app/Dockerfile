FROM openjdk:latest
RUN mkdir -p /var/log
RUN touch /var/log/logger.txt
ADD ../build/libs/app-0.0.2.jar spring-project-microservices.jar
ENTRYPOINT ["java", "-jar","spring-project-microservices.jar"]
EXPOSE 8080