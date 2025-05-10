FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/moneysavior-0.0.1-SNAPSHOT.jar moneysavior.jar

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=docker", "moneysavior.jar"]