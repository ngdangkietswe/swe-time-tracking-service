FROM eclipse-temurin:17.0.8.1_1-jre-jammy
WORKDIR /app
COPY /target/swe-time-tracking-service.jar swe-time-tracking-service.jar
ENTRYPOINT ["java", "-jar", "swe-time-tracking-service.jar"]
EXPOSE 7005 7050