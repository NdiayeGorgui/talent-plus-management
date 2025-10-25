FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar employeur-service.jar
ENTRYPOINT ["java", "-jar", "employeur-service.jar"]
EXPOSE 8088
