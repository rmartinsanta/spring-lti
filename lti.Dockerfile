FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
ADD wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ADD server/target/server-developing.jar app.jar
