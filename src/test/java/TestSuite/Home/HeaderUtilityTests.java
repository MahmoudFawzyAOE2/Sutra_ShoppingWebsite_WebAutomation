package TestSuite.Home;

import Pages.BasicMethods;
import Pages.HomePage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Time;
import java.util.List;

public class HeaderUtilityTests extends BaseTest {
    private HomePage homePage;
    private BasicMethods basicMethods;

    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        basicMethods = new BasicMethods(driver);
        homePage = new HomePage(driver);

        // Go to the main page
        driver.get(mainURL);
    }


    @Test(priority = 1)
    @Description("verify switching language functionality")
    public void homeLangToArabic() {

        WebElement langButton = driver.findElement(By.cssSelector("button.disclosure__button.dropdown-toggle.dropdown-label.localization-form__select.localization-selector.link.link--text.caption-large"));
        langButton.click();

        WebElement arabicButton = (driver.findElements(By.cssSelector("a.link.link--text.disclosure__link.d-flex.flex-align-center.focus-inset"))).get(0);
        String arabicLang = homePage.getElementText(arabicButton);
        arabicButton.click();

        langButton = driver.findElement(By.cssSelector("button.disclosure__button.dropdown-toggle.dropdown-label.localization-form__select.localization-selector.link.link--text.caption-large"));
        String pageLang = homePage.getElementText(langButton);

        Assert.assertEquals(arabicLang, pageLang);
    }

    @Test(priority = 2)
    @Description("verify registration page navigation")
    public void homeRegisterNav() throws InterruptedException {

        // load account widget
        homePage.loadAccountWidget();

        // locate register button
        WebElement registerButton = driver.findElement(By.cssSelector("a.button.button-2.button-register"));

        // assert that the button is displayed
        Assert.assertTrue(registerButton.isDisplayed(), "Button is not displayed");

        // assert the correct text on the button
        Assert.assertEquals(registerButton.getText().toLowerCase(), "create account", "Register Button has wrong text");

        // navigate to the register page
        registerButton.click();

        // assert correct navigation
        Assert.assertEquals(driver.getCurrentUrl(), "https://sutrastores.com/en/account/register", "Wrong URL");

    }


    @Test(priority = 3)
    @Description("verify registration page navigation")
    public void homeForgetPassNav() throws InterruptedException {

        // load account widget
        homePage.loadAccountWidget();

        // locate forget password link
        WebElement forgetPassLink = driver.findElement(By.cssSelector("a.auth-link.link.link-underline"));

        // assert that the link is displayed
        Assert.assertTrue(forgetPassLink.isDisplayed(), "Link is not displayed");

        // assert the correct text on the button
        Assert.assertEquals(homePage.getElementText(forgetPassLink).toLowerCase(), "forgot your password?", "forget password link has wrong text");

        // navigate to the register page
        forgetPassLink.click();

        // assert correct navigation
        Assert.assertEquals(driver.getCurrentUrl(), "https://sutrastores.com/en/account/login#recover", "Wrong URL");
    }



}
