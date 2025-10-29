FROM eclipse-temurin:24-jre
WORKDIR /app
COPY target/hotel-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
