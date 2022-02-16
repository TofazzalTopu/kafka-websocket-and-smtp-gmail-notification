# Push notification system 
### Push notification and email notification from gmail server (smtp) using websocket and kafka

Please follow below instruction to run this project:

###Browse swagger:
http://localhost:8080/swagger-ui.html

Technology stack:
1. Java - 11
2. Spring Boot
3. Spring Data JPA
4. Kafka
5. Websocket
6. SMTP (Gmail Server)
7. JWT
8. Maven
9. PostgreSQL
10. Swagger
11. Devtools
12. lombok

Run Commands:
1. mvn clean
2. mvn install
3. mvn spring-boot:run

Generate and run jar file:
1. mvn clean install.
2. cd target
3. java -jar notification-service.jar
    
### Create docker image and run
1. docker build -t notification-service .
2. docker run -p 8080:8080 notification-service
3. docker container run --name notification-service -p 8080:8080 -d notification-service
4. docker start <container id>
5. docker logs <container id>
