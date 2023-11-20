FROM eclipse-temurin:17-jdk
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
COPY build/libs/challenge-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]