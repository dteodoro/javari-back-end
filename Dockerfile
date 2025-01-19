FROM eclipse-temurin:17-jre as javari-auth
EXPOSE 8082
EXPOSE 5005
ENV JAVARI_DB_USER root
ENV JAVARI_DB_PASSWORD javari
COPY ./javari-auth/target/javari*.jar /app/app.jar
SHELL ["/bin/sh","-c"]
CMD ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17-jre as javari-connector
EXPOSE 8084
EXPOSE 5007
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENV JAVARI_DB_USER root
ENV JAVARI_DB_PASSWORD javari
COPY ./javari-connector/target/javari*.jar /app/app.jar
SHELL ["/bin/sh","-c"]
ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar

FROM eclipse-temurin:17-jre as javari-discovery
EXPOSE 8761
COPY ./javari-discovery/target/javari*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17-jre as javari-gateway
EXPOSE 8081
COPY ./javari-gateway/target/javari*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17-jre as resource
EXPOSE 8085
COPY ./resource/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17-jre as javari-game
EXPOSE 8083
EXPOSE 5005
ENV JAVARI_DB_USER root
ENV JAVARI_DB_PASSWORD javari
COPY ./javari-game/target/javari*.jar /app/app.jar
SHELL ["/bin/sh","-c"]
ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar

