# 🦾 FanCode

![build status](https://img.shields.io/github/actions/workflow/status/raviichunduru/RestAPI_Framework/trigger-new-updated-and-unit-tests-on-pull-request.yml?logo=GitHub)
![open issues](https://img.shields.io/github/issues/raviichunduru/RestAPI_Framework)
![forks](https://img.shields.io/github/forks/raviichunduru/RestAPI_Framework)
![stars](https://img.shields.io/github/stars/raviichunduru/RestAPI_Framework)
![license](https://img.shields.io/github/license/raviichunduru/RestAPI_Framework?style=flat-square)
![languages](https://img.shields.io/github/languages/count/raviichunduru/RestAPI_Framework)

## Application under test

[**JsonPlaceholder**](https://jsonplaceholder.typicode.com/) - An Free fake and reliable API for testing and prototyping

## 🔢 Requiring (one time) manual setup by user

1. [**JDK 11**](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) - as language of choice
   for writing this test framework.
2. [**Maven 3.8.6+**](https://maven.apache.org/) - for project dependency management and running tests.
3. [**Pre-Commit**](https://pre-commit.com/) - to have code automatically and uniformly formatted (JAVA, JSON, XML, YAML).
   - To install pre-commit Do below steps (as a one time activity).
   - Short version
      - Open terminal
      - Install [pre-commit](https://pre-commit.com/) (a hooks config manager).
         - If on mac, install using: `brew install pre-commit`
         - If on windows, install using pip (python config manager).
            - Install python and pip first if not intalled already.
            - Then run `pip install pre-commit`
      - Check pre-commit version by running: `pre-commit --version`
      - cd to your project repository.
      - Run `pre-commit install`
      - That's it! From now on if you try to push any unformatted code to GitHub, pre-commit hook will both format the code
        and show the changed file for you to stage and commit.

## 🚀 Objective of This Project

- This is an assignment project.
- To understand more about problem to solve. read [**here**](https://www.scribd.com/document/775685669/Fancode-SDET-Assignment).

## ⚙ Tool Set

Key tools to be used in this project are:

- [x] **Java** (As the core programming language)
- [x] **Maven** (for automatic dependency management)
- [x] **Junit 5** (for running tests)
- [x] **AssertJ** (for Fluent Assertions)
- [x] **RestAssured**  (library for Rest API automation)
- [x] **Slf4J/Log4J** (for logging interface and as a logging library)
- [x] **Typesafe** (for application configuration for multiple test environments)
- [x] **GitHub** (for version control)
- [x] **Badges** (for a quick view on project meta and build status)

## How to setup and run test?
1. Install Java JDK 11 or above
2. Clone repository: https://github.com/raviichunduru/ValidatingFanCodeCityUserTasks
3. That is it, You can run single test written for purpose of above-mentioned problem statement
   > mvn clean test
