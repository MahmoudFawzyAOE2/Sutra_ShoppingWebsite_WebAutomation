package TestSuite.Search;

import PagesMethods.SearchMethods;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class MainSearchTests extends BaseTest {

    private SearchMethods searchMethods;

    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        searchMethods = new SearchMethods(driver);
    }

    @Test (priority = 1)
    @Description("Check availability of page")
    public void smokeTest() {

        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());

        String expectedResult = "https://sutrastores.com/en";
        String actualResult = driver.getCurrentUrl();
        Assert.assertEquals(actualResult,  expectedResult);
    }

    @Test (priority = 2)
    @Description("verify searching functionality via URL (confirm search button)")
    public void searchFuncButton() {

        String itemToSearch = "jeans";
        searchMethods.searchForItem(itemToSearch);

        WebElement searchConfirmButton = driver.findElement(By.cssSelector("svg.icon-search-1.p-relative"));
        searchConfirmButton.click();

        String expectedResult = String.format("https://sutrastores.com/en/search?q=%s&options%%5Bprefix%%5D=last&type=product", itemToSearch);
        String actualResult = driver.getCurrentUrl();
        Assert.assertEquals(actualResult,  expectedResult);
    }

    @Test (priority = 3)
    @Description("verify searching functionality via URL (enter keyboard)")
    public void searchFuncEnter() {

        WebElement searchTextBox = searchMethods.searchForItem(SearchMethods.itemToSearch);
        searchTextBox.sendKeys(Keys.ENTER);

        String expectedResult = String.format("https://sutrastores.com/en/search?q=%s&options%%5Bprefix%%5D=last&type=product", SearchMethods.itemToSearch);
        String actualResult = driver.getCurrentUrl();
        Assert.assertEquals(actualResult,  expectedResult);
    }

    @Test (priority = 6)
    @Description("verify when left empty, a trending now will be displayed with clickable kewords")
    public void searchTrendingWidget() {

        // Opening Search Box
        WebElement searchTextBox = searchMethods.searchForItem("");
        searchTextBox.sendKeys("");

        // Locate Trending Widget Header
        WebElement headerTrending = driver.findElement(By.xpath("//*[@id=\"shopify-section-sections--18196977320172__9c03747e-b054-4db8-8194-5a35c71f82ca\"]/sticky-header/div/div/div[3]/details-modal/details/div/div/predictive-search/form/div[2]/div/div/div[1]/h3/span"));

        // verify that the header is displayed
        Assert.assertTrue(headerTrending.isDisplayed(), "Header is not displayed on the webpage.");

        // verify header text is "Trending Now"
        Assert.assertEquals(headerTrending.getText(),  "TRENDING NOW");

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
                Assert.assertEquals(childElement_aLink,  String.format("https://sutrastores.com/en/search?q=%s*&type=product", childElement_a.findElement(By.cssSelector("span")).getText()));

                /*
                // Open a new tab using JavaScript
                ((JavascriptExecutor) driver).executeScript("window.open()");

                // Switch to the new tab
                ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));

                // Open another webpage in the new tab
                driver.get(childElement_aLink);

                // Perform some actions on the new page
                WebElement anotherElement = driver.findElement(By.id("anotherElementId"));
                anotherElement.click();

                // Return to the original tab
                driver.switchTo().window(tabs.get(0));
                */
        }
    }
}
