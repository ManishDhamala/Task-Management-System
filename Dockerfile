FROM openjdk:28-ea-trixie
ADD target/Task-Management-System.jar Task-Management-System.jar
ENTRYPOINT ["java","-jar", "/Task-Management-System.jar"]

