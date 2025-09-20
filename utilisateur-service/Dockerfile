FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar utilisateur-service.jar
ENTRYPOINT ["java", "-jar", "utilisateur-service.jar"]
EXPOSE 8087
