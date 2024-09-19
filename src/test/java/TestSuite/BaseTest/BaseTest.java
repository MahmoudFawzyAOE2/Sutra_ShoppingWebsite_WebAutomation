package TestSuite.BaseTest;

import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public WebDriver driver;

    @BeforeMethod
    @Description("Common steps before and Test")
    public void beforeMethod() {

        // Create a new WebDriver instance (opens chrome)
        driver = new ChromeDriver();

        // Maximize the window
        driver.manage().window().maximize();

        // Go to the main page
        driver.get("https://sutrastores.com/en");

    }
/*
    @AfterMethod
    @Description("Common steps after and Test")
    public void afterMethod() {
        // Quit Session
        driver.quit();

    }


 */


}
