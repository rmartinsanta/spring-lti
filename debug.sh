mvn clean install
export LOADER_PATH=/home/poltatil/IdeaProjects/VideoQuizz/client-demo/target/
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar server/target/server-developing.jar 
