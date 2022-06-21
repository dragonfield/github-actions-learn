FROM amazoncorretto:17.0.3-alpine
ARG JAR_FILE=target/*-exec.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
