package TestSuite.Search;

import Pages.BasicMethods;
import Pages.SearchPage;
import TestData.TestData;
import TestSuite.BaseTest.BaseTest;
import URLs.URLs;
import jdk.jfr.Description;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class MainSearchTests extends BaseTest {

    private SearchPage searchMethods;
    private BasicMethods basicMethods;

    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        basicMethods = new BasicMethods(driver);
        searchMethods = new SearchPage(driver);

        // Go to the main page
        driver.get(URLs.mainURL);
    }

    @Test (priority = 1)
    @Description("Check availability of page")
    public void smokeTest() {

        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());

        String actualResult = driver.getCurrentUrl();
        Assert.assertEquals(actualResult,  URLs.mainURL);
    }

    @Test (priority = 2)
    @Description("verify searching functionality via URL (confirm search button)")
    public void searchFuncButton() {

        String itemToSearch = "jeans";
        searchMethods.searchForItemNoEnter(itemToSearch);

        WebElement searchConfirmButton = driver.findElement(By.cssSelector("svg.icon-search-1.p-relative"));
        searchConfirmButton.click();

        String expectedResult = String.format("https://sutrastores.com/en/search?q=%s&options%%5Bprefix%%5D=last&type=product", itemToSearch);
        String actualResult = driver.getCurrentUrl();
        Assert.assertEquals(actualResult,  expectedResult);
    }

    @Test (priority = 3)
    @Description("verify searching functionality via URL (enter keyboard)")
    public void searchFuncEnter() {

        searchMethods.searchForItem(TestData.realProduct);

        String expectedResult = URLs.searchItem(TestData.realProduct);
        String actualResult = driver.getCurrentUrl();
        Assert.assertEquals(actualResult,  expectedResult);
    }

    @Test (priority = 6)
    @Description("verify when left empty, a trending now will be displayed with clickable keywords")
    public void searchTrendingWidget() {

        // Opening Search Box
        WebElement searchTextBox = searchMethods.searchForItemNoEnter("");
        searchTextBox.sendKeys("");

        // Locate Trending Widget Header
        WebElement headerTrending = driver.findElement(By.xpath("//*[@id=\"shopify-section-sections--18196977320172__9c03747e-b054-4db8-8194-5a35c71f82ca\"]/sticky-header/div/div/div[3]/details-modal/details/div/div/predictive-search/form/div[2]/div/div/div[1]/h3/span"));

        // verify that the header is displayed
        Assert.assertTrue(headerTrending.isDisplayed(), "Header is not displayed on the webpage.");

        // verify header text is "Trending Now"
        Assert.assertEquals(headerTrending.getText().toLowerCase(), TestData.trending);

        // locate the Trending elements
        List<WebElement> elementsTrending = driver.findElements(By.xpath("//*[@id=\"shopify-section-sections--18196977320172__9c03747e-b054-4db8-8194-5a35c71f82ca\"]/sticky-header/div/div/div[3]/details-modal/details/div/div/predictive-search/form/div[2]/div/div/div[1]/ul/li"));

        for (WebElement element : elementsTrending) {

                // verify that each element is displayed
                Assert.assertTrue(element.isDisplayed(), "Element is not displayed on the webpage.");

                // locate child tag
                WebElement childElement_a = element.findElement(By.cssSelector("a"));

                // verify that element name displayed
                Assert.assertTrue(childElement_a.isDisplayed(), "Link is not displayed on the webpage.");

                // get the link to the element search page
                String childElement_aLink = childElement_a.getAttribute("href");

                // get element name
                childElement_a.findElement(By.cssSelector("span")).getText();

                // verify that the element has the link to its own product search page
                Assert.assertEquals(childElement_aLink, URLs.searchproduct(childElement_a.findElement(By.cssSelector("span")).getText()) );
        }
    }
}
