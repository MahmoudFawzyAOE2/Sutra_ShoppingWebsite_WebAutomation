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
import java.util.List;

public class SearchItemNumberTests extends BaseTest {

    private SearchMethods searchMethods;
    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        searchMethods = new SearchMethods(driver);
    }

    @Test(priority = 1)
    @Description("verify correct number of search results on result page ")
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

    @Test(priority = 2)
    @Description("verify that No search can occur without text to be inserted")
    public void searchNumOfItemsZero() {

        // Search for nothing
        WebElement searchTextBox = searchMethods.searchForItem("");
        searchTextBox.sendKeys(Keys.ENTER);

        // verify that url didn't change
        Assert.assertEquals(driver.getCurrentUrl(), "https://sutrastores.com/en");
    }
}
