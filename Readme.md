<!--
# Project still under development, come back soon :)

https://docs.google.com/spreadsheets/d/16S6UtNJoANOlI1Bh0P6OvgcrKOj71fIdc1jRWkltQ9E/edit?gid=0#gid=0
-->
# Sutra Web Automation Testing Project

## Table of Contents
- [Description](#description)
- [Tools and Technologies](#tools-and-technologies)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Test Excution](#test-excution)
- [Configuration](#configuration)
- [Authors](#authors)

## Description
Sutra Web Automation Testing Project is a test automation framework tailored for testing core functionalities in this E-commerce website. It provides a structured approach to testing and validating various functionalities, ensuring reliability and efficiency in the testing process. This framework facilitates testing functionalities for searching, retrieving, and navigating through the website pages using Selenium WebDriver.

## Tools and Technologies
<a href="https://www.java.com/en/"><img src="https://cdn-icons-png.flaticon.com/512/226/226777.png" alt="c" width="60" height="60"/> </a>
<a href="https://www.selenium.dev/"> <img src="https://miro.medium.com/v2/resize:fit:1400/1*musVE9e4bgjTWeoRmc-P_w.png" width="60" height="60"/> </a>
<a href="https://testng.org/"> <img src="https://howtodoinjava.com/wp-content/uploads/2014/12/TestNG.png" width="60" height="60"/> </a>
<a href="https://allurereport.org/"> <img src="https://avatars.githubusercontent.com/u/5879127?s=280&v=4" width="60" height="60"/> </a>

## Installation
To set up the project locally, follow these steps:

1. Clone the repository (using bash or Github Desktop):
    ```bash
    git clone https://github.com/MahmoudFawzyAOE2/Sutra_ShoppingWebsite_WebAutomation.git
    ```
2. Open the Project in a proper IDE (Intelij Idea, Eclipse,...)
   
6. Reload the project to ensure that the dependencies are downloaded correctly

## Project Structure
This project includes testing requests related to 4 functionalities, each in a separate directory
* Home Page
* Accessories Page (as an example of a specific product category)
* Search Functionality
* Register Functionality

Test Automation Features added to this project:
* Test Listeners: To prevent the driver from closing the window in case of test failure to help debug
* Screenshots of the found bugs
* Handling the test data in separate Java classes to improve maintainability

## Test Excution
To run the entire test scenario, excute `TestNG.xml` file. or run a single test independently.

Test results will be available in the `allure-results` directory [allure setup needed]

```bash
allure serve PATH\TO\PROJECT\Sutra_ShoppingWebsite_WebAutomation\allure-results
```

**---------------Report Screenshots to be added-------------------**
<!--
<div align="center"><img src="https://github.com/user-attachments/assets/9b090ff3-5c55-479f-bc34-b389eac30be1" alt="c" width="800" height="300"/> </div>
<div align="center"><img src="https://github.com/user-attachments/assets/80431601-a952-4172-bbe1-2f800c2a806e" alt="c" width="800" height="550"/> </div>
-->
## Configuration
Update the `TestNG.xml` file to configure the test suite. 

## Authors
- Mahmoud Fawzy - [MahmoudFawzyAOE2](https://github.com/MahmoudFawzyAOE2)
- Mohanad Ehab - [Mohanadehab](https://github.com/Mohanadehab)
