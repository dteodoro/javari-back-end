# Build do módulo 1
FROM openjdk:17
ARG SERVICE=javari-auth
ARG SERVER_PORT=8080
COPY ./$SERVICE/target/javari*.jar /app/app.jar
EXPOSE $SERVER_PORT
CMD ["java", "-jar", "/app/app.jar"]
