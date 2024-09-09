# Build do módulo 1
FROM openjdk:17
ARG SERVICE=javari-auth
COPY ./$SERVICE/target/javari*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
