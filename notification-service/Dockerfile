FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar notification-talent-service.jar
ENTRYPOINT ["java", "-jar", "notification-talent-service.jar"]
EXPOSE 8082
