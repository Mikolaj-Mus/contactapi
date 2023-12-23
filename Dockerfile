FROM openjdk:17
EXPOSE 8080
ADD target/contactapi.jar contactapi.jar
ENTRYPOINT ["java", "-jar", "/contactapi.jar"]