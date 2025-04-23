FROM maven:3.9.9-eclipse-temurin-21 AS build
RUN mkdir /opt/smart-recycle
COPY . /opt/smart-recycle
WORKDIR /opt/smart-recycle
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine
RUN mkdir /opt/smart-recycle
COPY --from=build /opt/smart-recycle/target/smart-recycle-0.0.1-SNAPSHOT.jar /opt/smart-recycle/smart-recycle.jar
WORKDIR /opt/smart-recycle
ENV PROFILE=prd
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=$PROFILE -jar smart-recycle.jar"]
