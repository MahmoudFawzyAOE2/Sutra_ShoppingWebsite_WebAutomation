package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RegisterPage {
    public WebDriver driver;
    private JavascriptExecutor js;
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;

    }

    // URLs for the fake email and registration page
    public static String registerURL = "https://sutrastores.com/en/account/register";
    public static String fakeEmailWebsiteURL = "https://email-fake.com/";

    // Method to generate a fake email
    public String getFakeEmail() {
        // Open the fake email generator page
        driver.get(fakeEmailWebsiteURL);

        // Locate the button to generate an email
        List<WebElement> emailButtons = driver.findElements(By.cssSelector("button.fem.btn.btn-su2.waves-effect.waves-light.waves-raised"));

        // Click the generate email button
        emailButtons.get(1).click();

        // Get the generated email text
        WebElement emailParagraph = driver.findElement(By.cssSelector("p b span#email_ch_text"));
        return emailParagraph.getText();
    }

    // Method to open a new tab and navigate to the registration page
    public void openRegistrationPageInNewTab() {
        // Open a new tab
        ((JavascriptExecutor) driver).executeScript("window.open()");

        // Switch to the new tab
        String newTabHandle = driver.getWindowHandles().stream().filter(handle -> !handle.equals(driver.getWindowHandle())).findFirst().get();
        driver.switchTo().window(newTabHandle);

        // Navigate to the registration page URL
        driver.get(registerURL);
    }
    public void submitRegistrationForm() throws InterruptedException {
        // Locate the submit button and click it


    }

    public void registerUser(String firstName, String lastName, String email, String password) throws InterruptedException {
        // Locate the registration input fields
        List<WebElement> registerFields = driver.findElements(By.cssSelector("input.form-input.form-input-placeholder"));

        // Fill in the registration form
        registerFields.get(0).sendKeys(firstName);
        registerFields.get(1).sendKeys(lastName);
        registerFields.get(2).sendKeys(email);
        registerFields.get(3).sendKeys(password);

        // Locate and click the submit button
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Create An Account']"));
        registerButton.click();
        Thread.sleep(500);
        registerButton.click();

        // Optionally, wait for the page to load after submission
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }
    public boolean isFieldValid(String fieldId) {
        WebElement field = driver.findElement(By.id(fieldId));
        return (Boolean) js.executeScript("return arguments[0].checkValidity();", field);

    }
    public void validateEmailField(String firstName, String lastName, String email, String password, String expectedMessage) throws InterruptedException {
        // Open registration page
      openRegistrationPageInNewTab();
        // Register user
        registerUser(firstName, lastName, email, password);
        // Validate the email field
        boolean isEmailFieldInvalid = isFieldValid("RegisterForm-email");

        // Print validation result
        if (!isEmailFieldInvalid) {
            System.out.println("Validation message appeared: " + expectedMessage);
        } else {
            System.out.println("No validation message, the field is valid.");
        }
    }


}
