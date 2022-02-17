FROM maven:3.6.3-adoptopenjdk-11 as build

COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN  mvn clean package -DskipTests=true \
  && mv ./target/jsonGenerator-0.0.1-SNAPSHOT.jar ./jsonGenerator.jar

EXPOSE 8084

ENTRYPOINT ["java","-jar","/jsonGenerator.jar"]