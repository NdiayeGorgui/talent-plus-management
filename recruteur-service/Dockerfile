FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar recruteur-service.jar
ENTRYPOINT ["java", "-jar", "recruteur-service.jar"]
EXPOSE 8084
