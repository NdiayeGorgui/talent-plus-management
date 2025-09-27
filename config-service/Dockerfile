FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]
EXPOSE 9191
