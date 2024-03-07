FROM openjdk:19-jdk-slim as build

WORKDIR /app
COPY . /app

RUN chmod +x gradlew && ./gradlew build

FROM openjdk:19-jdk-slim

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/exchange-app.jar

EXPOSE 8080

CMD ["java","-jar","/app/exchange-app.jar"]