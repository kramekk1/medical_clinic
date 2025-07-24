FROM openjdk:21
MAINTAINER kramekk1@gmail.com
COPY target/medicalClinic-0.0.1-SNAPSHOT.jar medicalClinic-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","medicalClinic-0.0.1-SNAPSHOT.jar"]

