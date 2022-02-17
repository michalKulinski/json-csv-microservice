FROM maven:3.6.3-adoptopenjdk-11 as build

COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN  mvn clean package -DskipTests=true \
  && mv ./target/csvConverter-0.0.1-SNAPSHOT.jar ./csvConverter.jar

EXPOSE 8085

ENTRYPOINT ["java","-jar","/csvConverter.jar"]