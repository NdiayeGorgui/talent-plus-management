FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar statistic-service.jar
ENTRYPOINT ["java", "-jar", "statistic-service.jar"]
EXPOSE 8086
