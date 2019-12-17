FROM openjdk:10-jdk
VOLUME /tmp
ARG JAR_FILE
ADD server/target/server-developing.jar app.jar
ADD wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
