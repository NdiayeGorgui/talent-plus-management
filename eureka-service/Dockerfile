FROM openjdk:17-oracle
VOLUME /tmp
COPY target/eureka-service-0.0.1-SNAPSHOT.jar eureka-service.jar
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]
EXPOSE 8761