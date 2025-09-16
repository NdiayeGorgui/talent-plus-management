FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar candidat-service.jar
ENTRYPOINT ["java", "-jar", "candidat-service.jar"]
EXPOSE 8081
