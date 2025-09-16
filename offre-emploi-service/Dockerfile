FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar offre-service.jar
ENTRYPOINT ["java", "-jar", "offre-service.jar"]
EXPOSE 8083
