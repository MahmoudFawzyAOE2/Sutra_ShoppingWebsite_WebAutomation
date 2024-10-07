package TestSuite.BaseTest;

import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    public WebDriver driver;
    public static String mainURL = "https://sutrastores.com/en" ;
    @BeforeMethod
    @Description("Common steps before and Test")
    public void beforeMethod() {

        // Create a new WebDriver instance (opens chrome)
        driver = new ChromeDriver();

        // Maximize the window
        driver.manage().window().maximize();
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
// alternative Base test
/*package TestSuite.BaseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected String mainURL = "https://sutrastores.com/en/";

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
*/
