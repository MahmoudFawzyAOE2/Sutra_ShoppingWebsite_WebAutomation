package TestSuite.Register;

import Pages.BasicMethods;
import Pages.RegisterPage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
public class MainRegisterTests extends BaseTest {

    RegisterPage registerPage;

    @BeforeMethod
    public void setUp() {
        // Initialize BasicMethods and RegisterPage
        BasicMethods basicMethods = new BasicMethods(driver);
        registerPage = new RegisterPage(driver);
    }
    /*private void validateEmailField(String firstName, String lastName, String email, String password, String expectedMessage) throws InterruptedException {
        // Open registration page
        registerPage.openRegistrationPageInNewTab();
        // Register user
        registerPage.registerUser(firstName, lastName, email, password);
        // Validate the email field
        boolean isEmailFieldInvalid = registerPage.isFieldValid("RegisterForm-email");

        // Print validation result
        if (!isEmailFieldInvalid) {
            System.out.println("Validation message appeared: " + expectedMessage);
        } else {
            System.out.println("No validation message, the field is valid.");
        }
    }
*/
    @Test(priority = 1)
    @Description("Check availability of page")
    public void smokeTest() throws InterruptedException {
        // Generate a fake email using the RegisterPage class method
        String fakeEmail = registerPage.getFakeEmail();

        // Open the registration page in a new tab
        registerPage.openRegistrationPageInNewTab();

        // Locate the registration input fields
        List<WebElement> registerFields = driver.findElements(By.cssSelector("input.form-input.form-input-placeholder"));
        /*
        0: First Name
        1: Last Name
        2: Email
        3: Password
        */

        registerPage.registerUser("Mohanad", "Ehab", fakeEmail, "123456789");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://sutrastores.com/en/challenge"));
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, "https://sutrastores.com/en/challenge", "Registration did not redirect to the expected page.");

    }

    @Test(priority = 2)
    @Description("Verify Popup Message Appearance for Invalid Email(Empty)")
    public void testEmptyEmail() throws InterruptedException {

        registerPage.openRegistrationPageInNewTab();


        registerPage.registerUser("Test", "User", "", "12345");

        //WebElement CaptchaButton = driver.findElement(By.id("checkbox"));
       // WebElement SubmitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
        //CaptchaButton.click();
        //SubmitButton.click();
        //String actualURL = driver.getCurrentUrl();
        WebElement emailField = driver.findElement(By.id("RegisterForm-email"));


        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isEmailFieldInvalid = (Boolean) js.executeScript("return arguments[0].checkValidity();", emailField);

        if (Boolean.FALSE.equals(isEmailFieldInvalid)) {
            System.out.println("Validation message appeared: Please fill out this field.");
        } else {
            System.out.println("No validation message, the field is valid.");
        }


    }

    @Test(priority = 3)
    @Description("Verify Popup Message Appearance for Invalid Password(Empty)")
    public void testEmptyPassword() throws InterruptedException {
        // Generate a fake email
        String fakeEmail = registerPage.getFakeEmail();
        // Open the registration page
        registerPage.openRegistrationPageInNewTab();
        WebElement passwordField = driver.findElement(By.id("RegisterForm-password"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean isEmailFieldInvalid = (Boolean) js.executeScript("return arguments[0].checkValidity();", passwordField);

        if (Boolean.FALSE.equals(isEmailFieldInvalid)) {
            System.out.println("Validation message appeared: Please fill out this field.");
        } else {
            System.out.println("No validation message, the field is valid.");
        }

    }
    @Test(priority = 4)
    @Description("Verify Popup Message Appearance for Invalid Email (Without '@')")
    public void testEmailWithoutAt() throws InterruptedException {
        registerPage.validateEmailField("Test", "User", "test.user", "12345", "Please include an '@' in the email address. 'test.user' is missing an '@'.");
    }
    @Test(priority = 5)
    @Description("Verify Popup Message Appearance for Invalid Email (With nothing after '.')")
    public void testEmailWithNothingAfterDot() throws InterruptedException {
        registerPage.validateEmailField("Test", "User", "test@user.", "12345", "'.' is used at wrong position in test@user.");
    }
    @Test(priority = 3 )
    @Description("Verify Popup Message Appearance for Invalid Email(With anything after .)")
    public void testEmailWithNothingAfterAt() throws InterruptedException {
        registerPage.validateEmailField("Test", "User", "test@user.", "12345", "'.' is used at wrong position in test@user.");

    }

    }

