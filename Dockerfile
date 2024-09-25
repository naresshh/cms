FROM openjdk:17
EXPOSE 8080
ADD target/com.candidate-0.0.1-SNAPSHOT.jar.original cmsimage1.jar
ENTRYPOINT ["java","-jar","/cmsimage1.jar"]
