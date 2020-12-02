FROM adoptopenjdk/openjdk14:ubi
EXPOSE 8080
ADD /target/manager-0.0.1-SNAPSHOT.jar manager.jar
ENTRYPOINT ["java","-jar","manager.jar"]