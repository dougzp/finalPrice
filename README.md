# Getting Started
### Project Setup
This project uses Gradle as its build tool and is configured with the Spring Boot framework. The main features integrated include JPA for data persistence, reactive web components for asynchronous operations, and development tools for enhanced developer experience.

### Reference Documentation
For an in-depth understanding of the used tools and frameworks, consider the following resources:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.3/gradle-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#appendix.configuration-metadata.annotation-processor)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#web.reactive)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.1.3/reference/htmlsingle/index.html#using.devtools)
### Guides
To get hands-on with the features, refer to:
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)


### Test Coverage with Jacoco
This project is configured with Jacoco, a code coverage library which ensures that your code is adequately covered by tests.

* Running Tests with Jacoco
* Navigate to the project root directory in your terminal.

Execute the following command:


```
./gradlew clean
./gradlew test
./gradlew jacocoTestReport
```
This will run the tests and generate a coverage report.

# Viewing the Jacoco Report
Once the tests have completed, the Jacoco report can be found in:


```
/build/reports/jacoco/test/html/index.html
```
Open this file in any web browser to view the coverage details.

### Cucumber Testing
To execute the application tests and generate the Cucumber report:

```
./gradlew clean
./gradlew build

```
Upon completion, the test report can be accessed at:
```
reports/cucumber/report
```
Open html file in a browser to see detailed test outcomes.


### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
