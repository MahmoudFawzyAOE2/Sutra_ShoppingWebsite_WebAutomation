package TestSuite.BaseTest;

import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public WebDriver driver;

    @BeforeClass
    @Description("Common steps before and Test")
    public void beforeClass() {

        // Create a new WebDriver instance (opens chrome)
        driver = new ChromeDriver();

        // Maximize the window
        driver.manage().window().maximize();

        // Go to the main page
        driver.get("https://sutrastores.com/en");


    }
    /*
    @AfterClass
    @Description("Common steps after and Test")
    public void afterClass() {

        // Quit Session
        driver.quit();

    }

    */


}
