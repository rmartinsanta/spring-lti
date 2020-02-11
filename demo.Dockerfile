FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
ADD client-demo/target/client-demo-developing.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
