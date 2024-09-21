package TestSuite.Search;

import PagesMethods.SearchMethods;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class SearchItemDisplayTests extends BaseTest {
    private SearchMethods searchMethods;
    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        searchMethods = new SearchMethods(driver);
    }

    @Test(priority = 1)
    @Description("verify correct items displayed when searching for \"x\"")
    public void searchActualItemsCheck() {

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

    @Test(priority = 2)
    @Description("verify no items displayed when searching for a non existing item  ")
    public void searchFictionalItemsCheck() {

        WebElement searchTextBox = searchMethods.searchForItem("Banana");
        searchTextBox.sendKeys(Keys.ENTER);

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // Assert that no elements are displayed
        Assert.assertEquals(numberOfElements,  0, "There are elements displayed");

        for (WebElement element : textElements) {
            String itemName= element.getText();

            boolean containsExpectedItem = Arrays.stream(SearchMethods.expectedItems)
                    .anyMatch(item -> itemName.contains(item));

            // Assert that every item name has a keyword related to the searched word
            Assert.assertTrue(containsExpectedItem, "Item Name \"" + itemName+ "\" is not a correct item as it has no related keyword");
        }
    }
}
