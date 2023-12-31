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


### Running the Application
To clean, build, and run the application:

```
./gradlew clean
./gradlew build
./gradlew bootRun
```
Your application will start and should be accessible on http://localhost:8080.

Testing the Endpoint
You can test the endpoint using curl. Here's an example:

```
curl http://localhost:8080/getPrice/2020-06-15-00.00.00/35455/ZARA
```
### Continuous Integration

On every pull request and push to the `master` branch, the pipeline:

1. Sets up the necessary environment.
2. Runs the project tests with `./gradlew test`.
3. Generates a code coverage report with JaCoCo.

### Continuous Deployment

Upon merging to the `master` branch:

1. The pipeline builds the project.
2. Deploys to [specify where you deploy, e.g., a specific server, cloud provider, etc.]

You can view the workflow configurations in the `.github/workflows` directory.

### CI/CD Status

You can check the status of the recent pipeline runs on the [GitHub Actions Dashboard](https://github.com/dougzp/finalPrice/action).

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
