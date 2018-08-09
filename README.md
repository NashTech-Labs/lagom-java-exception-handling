# lagom-java-exception-handling

### Pre-requisites 
    jdk8
    maven
        
### Compiling the service
    mvn clean compile

### Running the service
    mvn lagom:runAll
    
#### Example ServiceCall
    Method type: GET
    URL example: http://localhost:9000/read?color=DARKSALMON

    
### Running Unit Test Cases
    mvn clean test

### Running Checkstyle
    mvn verify
    Check -- */target/checkstyle/checkstyle-output.xml files for results
