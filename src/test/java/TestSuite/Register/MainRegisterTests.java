package TestSuite.Register;

import Pages.BasicMethods;
import Pages.RegisterPage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class MainRegisterTests extends BaseTest {

    private RegisterPage registerPage;
    private BasicMethods basicMethods;

    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        basicMethods = new BasicMethods(driver);
        registerPage = new RegisterPage(driver);
    }

    @Test(priority = 1)
    @Description("Check availability of page")
    public void smokeTest() {
        // URL 1
        driver.get(RegisterPage.fakeEmailWebsiteURL);

        List<WebElement> emailButtons = driver.findElements(By.cssSelector("button.fem.btn.btn-su2.waves-effect.waves-light.waves-raised"));

        // Click generate Email button
        emailButtons.get(1).click();

        WebElement emailParagraph = driver.findElement(By.cssSelector("p b span#email_ch_text"));
        String email = emailParagraph.getText();

        // Open a new tab
        ((JavascriptExecutor) driver).executeScript("window.open()");

        // Switch to the new tab
        String newTabHandle = driver.getWindowHandles().stream().filter(handle -> !handle.equals(driver.getWindowHandle())).findFirst().get();
        driver.switchTo().window(newTabHandle);

        // URL 2
        driver.get(RegisterPage.registerURL);

        List<WebElement> registerFields  = driver.findElements(By.cssSelector("input.form-input.form-input-placeholder"));
        /*
        0: First Name
        1: Last Name
        2: Email
        3: Password
        */

        registerFields.get(2).sendKeys(email);

        /*
        WebElement emailField  = driver.findElement(By.cssSelector("input#RegisterForm-email"));
        emailField.sendKeys(email);
        */




    }
}
