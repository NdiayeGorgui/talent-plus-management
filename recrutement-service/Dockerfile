FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar recrutement-service.jar
ENTRYPOINT ["java", "-jar", "recrutement-service.jar"]
EXPOSE 8083
