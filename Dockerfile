#FROM anapsix/alpine-java
#MAINTAINER mgw
#COPY ./target/microgateway-enterprise-demo-1.0-SNAPSHOT.jar /home/microgateway-enterprise-demo-1.0-SNAPSHOT.jar
#CMD ["java","-jar","/home/microgateway-enterprise-demo-1.0-SNAPSHOT.jar"]

#FROM openjdk:8-jdk-alpine

#ARG JAR_FILE=target
#ENTRYPOINT ["java","-jar","/microgateway-demo.jar"]


ARG HAZELCAST_VERSION=3.12.12-1
FROM hazelcast/hazelcast:$HAZELCAST_VERSION
MAINTAINER mgw

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} microgateway-demo.jar
# Adding custom JARs to the classpath
ENTRYPOINT ["java","-jar","/microgateway-demo.jar"]
