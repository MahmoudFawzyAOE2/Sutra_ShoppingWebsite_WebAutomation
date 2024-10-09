package TestSuite.Home;

import Listeners.CustomListener;
import TestData.TestData;
import URLs.URLs;
import Pages.BasicMethods;
import Pages.HomePage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListener.class)

public class FooterTests extends BaseTest {

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
    @Description("verify facebook social media account")
    public void homeFacebookNav() {

        // locate the socialMedia element
        WebElement socialMedia = driver.findElement(By.cssSelector("a.link.link--text.list-social__link.icon-facebook"));
        socialMedia.click();

        Assert.assertEquals(driver.getCurrentUrl(), URLs.facebook ,"Link Mismatch.");

        WebElement closeButton = driver.findElement(By.cssSelector("div[role='button'][aria-label='Close']"));
        closeButton.click();

        WebElement headerOne = driver.findElement(By.cssSelector("h1.html-h1.xdj266r.x11i5rnm.xat24cr.x1mh8g0r.xexx8yu.x4uap5.x18d9i69.xkhd6sd.x1vvkbs.x1heor9g.x1qlqyl8.x1pd3egz.x1a2a7pz"));

        String headerOneText = headerOne.getText().toLowerCase();
        Assert.assertTrue(headerOneText.contains(TestData.brandName), "Text Mismatch.");

    }

    @Test(priority = 1)
    @Description("verify instagram social media account")
    public void homeInstagramNav() {

        // locate the socialMedia element
        WebElement socialMedia = driver.findElement(By.cssSelector("a.link.link--text.list-social__link.icon-instagram"));
        socialMedia.click();

        Assert.assertEquals(driver.getCurrentUrl(), URLs.instagram ,"Link Mismatch.");

        WebElement headerOne = driver.findElement(By.cssSelector("span.x1lliihq.x1plvlek.xryxfnj.x1n2onr6.x1ji0vk5.x18bv5gf.x193iq5w.xeuugli.x1fj9vlw.x13faqbe.x1vvkbs.x1s928wv.xhkezso.x1gmr53x.x1cpjm7i.x1fgarty.x1943h6x.x1i0vuye.xvs91rp.x1s688f.x5n08af.x10wh9bi.x1wdrske.x8viiok.x18hxmgj"));

        String headerOneText = headerOne.getText().toLowerCase();
        Assert.assertTrue(headerOneText.contains(TestData.brandName), "Text Mismatch.");

    }

    @Test(priority = 1)
    @Description("verify tiktok social media account")
    public void homeTiktokNav() {

        // locate the socialMedia element
        WebElement socialMedia = driver.findElement(By.cssSelector("a.link.link--text.list-social__link.icon-tiktok"));
        socialMedia.click();

        Assert.assertEquals(driver.getCurrentUrl(), URLs.tik_tok ,"Link Mismatch.");


        WebElement headerOne = driver.findElement(By.xpath("//h1[@data-e2e='user-title']"));
        String headerOneText = headerOne.getText().toLowerCase();
        Assert.assertTrue(headerOneText.contains(TestData.brandName), "Text Mismatch.");

        WebElement headerTwo = driver.findElement(By.xpath("//h1[@data-e2e='user-subtitle']"));
        String headerTwoText = headerTwo.getText().toLowerCase();
        Assert.assertTrue(headerTwoText.contains(TestData.brandName), "Text Mismatch.");

    }


}
