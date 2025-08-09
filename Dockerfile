
# Use a Maven base image with Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy all project files, including testng.xml
COPY . .

# Run the tests to generate the Allure results
# This step might fail if tests fail, but it's crucial to get the results.
RUN mvn test -DbaseUrl=https://fakerestapi.azurewebsites.net

# Generate the Allure report from the results
# The allure:report goal will be executed after the test run
# The report is generated in the 'target/site/allure-maven-plugin/index.html' path by default
# A simple way to get it to a known folder would be to use 'docker cp' on that path.
# A better way would be to just copy the results folder
# and have the GitHub action generate the report instead
