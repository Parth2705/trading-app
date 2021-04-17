FROM openjdk:8-jdk-alpine
EXPOSE 8087
ARG JAR_FILE=build/libs/stock.transaction-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]