/*

package TestSuite.BaseTest;

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

    @AfterMethod
    @Description("Common steps after and Test")
    public void afterMethod() {
        // Quit Session
        driver.quit();
    }

}
*/



// alternative Base test
package TestSuite.BaseTest;

import Listeners.CustomListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.time.Duration;

@Listeners(CustomListener.class)

public class BaseTest {
    protected WebDriver driver;
    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void teardown(ITestContext context) {
        if (CustomListener.testFailed) {
            System.out.println("Test failed. Keeping browser open for debugging.");
            return; // Skip cleanup if any test failed
        }

        if (driver != null) {
            driver.quit();
        }
    }

}

