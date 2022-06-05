#base image
FROM openjdk:17-jdk-alpine
VOLUME /tmp
RUN addgroup -S jumbo && adduser -S jumbo -G jumbo
USER jumbo:jumbo
COPY target/*.jar ./app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
