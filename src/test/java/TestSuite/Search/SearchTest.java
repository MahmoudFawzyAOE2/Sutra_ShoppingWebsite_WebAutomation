package TestSuite.Search;

import PagesMethods.SearchMethods;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SearchTest extends BaseTest {

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

    @Test (priority = 4)
    @Description("verify correct items displayed when searching for \"x\"")
    public void searchItemsCheck() {

        WebElement searchTextBox = searchMethods.searchForItem(SearchMethods.itemToSearch);
        searchTextBox.sendKeys(Keys.ENTER);

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // Scroll Down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 10000);"); // Scrolls down by 10000 pixels

        // wait for elements to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"), numberOfElements));

        // get the elements after new elements loaded
        textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));

        for (WebElement element : textElements) {
            String itemName= element.getText();

            boolean containsExpectedItem = Arrays.stream(SearchMethods.expectedItems)
                    .anyMatch(item -> itemName.contains(item));
            
            // Assert that every item name has a keyword related to the searched word
            Assert.assertTrue(containsExpectedItem, "Item Name \"" + itemName+ "\" is not a correct item as it has no related keyword");
        }
    }

    @Test (priority = 5)
    @Description("verify searching functionality via URL (enter keyboard)")
    public void searchNumOfItems() {

        WebElement searchTextBox = searchMethods.searchForItem(SearchMethods.itemToSearch);
        searchTextBox.sendKeys(Keys.ENTER);

        /* 1) get number of items by counting elements */
        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // Scroll Down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 10000);"); // Scrolls down by 10000 pixels

        // wait for elements to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"), numberOfElements));

        // get number of elements
        textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        numberOfElements = textElements.size();
        System.out.println(numberOfElements);

        /* 2) get number of items from the header */
        WebElement searchPageHeader = driver.findElement(By.cssSelector("h1.page-header.text-center.scroll-trigger.animate--slide-in"));
        String searchPageHeaderText = searchPageHeader.getText();

        // Extract the numbers using regular expression
        String numberOfItems = searchPageHeaderText.replaceAll("[^0-9]", "");

        // Convert the string to an integer
        int numberOfItemsINT = Integer.parseInt(numberOfItems);
        System.out.println(numberOfItemsINT);

        Assert.assertEquals(numberOfElements,  numberOfItemsINT, "Number of Items are not the same");

    }

    @Test (priority = 6)
    @Description("verify searching functionality via URL (enter keyboard)")
    public void searchTrendingWidget() {

        WebElement searchTextBox = searchMethods.searchForItem("");
        searchTextBox.sendKeys("");

        WebElement headerTrending = driver.findElement(By.xpath("//*[@id=\"shopify-section-sections--18196977320172__9c03747e-b054-4db8-8194-5a35c71f82ca\"]/sticky-header/div/div/div[3]/details-modal/details/div/div/predictive-search/form/div[2]/div/div/div[1]/h3/span"));

        // verify that header id displayed
        Assert.assertTrue(headerTrending.isDisplayed(), "Header is not displayed on the webpage.");

        // verify header text is "Trending Now"
        Assert.assertEquals(headerTrending.getText(),  "TRENDING NOW");

        List<WebElement> elementsTrending = driver.findElements(By.xpath("//*[@id=\"shopify-section-sections--18196977320172__9c03747e-b054-4db8-8194-5a35c71f82ca\"]/sticky-header/div/div/div[3]/details-modal/details/div/div/predictive-search/form/div[2]/div/div/div[1]/ul/li"));

        for (WebElement element : elementsTrending) {

                Assert.assertTrue(element.isDisplayed(), "Element is not displayed on the webpage.");

                WebElement childElement_a = element.findElement(By.cssSelector("a"));
                childElement_a.findElement(By.cssSelector("span")).getText();

                // verify that header id displayed
                Assert.assertTrue(childElement_a.isDisplayed(), "Header is not displayed on the webpage.");

                String childElement_aLink = childElement_a.getAttribute("href");

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
