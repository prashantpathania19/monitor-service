#Introduction:
This is the monitor-service service.

#Tech Stack Used
   - Jdk 11
   - Spring Boot (Version 2.2.6)
   - MySql (mysql Ver 14.14 Distrib 5.7.32)
   - Maven (Version 3.5.4)
   - HTML with Vanilla JS

#How to setup development environment
   - assume that project is at location /Users/prashant/projects/monitor-service
   - create database schema "monitor" in mysql db
   - run server/db-scripts/1.sql
   - open with Intellij by pointing to /Users/prashant/projects/monitor-service
   - go to /Users/prashant/projects/monitor-service
   - in terminal, switch to java 11 (if you are still using different version, after installing use below command:
     - export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.10.jdk/Contents/Home
     - echo $JAVA_HOME
   - run mvn clean for build the project
   - start spring-boot at port 9090, change DB_URL, DB_USERNAME, DB_PASSOWORD in the following command and run it in console:
     - mvn clean spring-boot:run -Dspring-boot.run.arguments="--SERVER_PORT=9090 --DB_URL=jdbc:mysql://localhost:3306/monitor --DB_USERNAME=root --DB_PASSWORD=5S182vt2" -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
   - go to http://localhost:9090/monitor-service/swagger-ui.html, you'll find the list of the api's used
   - you can remote debug with port 5005

#After running service, url's to check:
  - Monitor Service Page
    - http://localhost:9090/monitor-service/form
  - healthcheck:
    - http://localhost:9090/monitor-service/health/short
    - http://localhost:9090/monitor-service/health/detail
  - version:
    - http://localhost:9090/monitor-service/version
  - Swagger
    - http://localhost:9090/monitor-service/swagger-ui.html

#Document
https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/reference