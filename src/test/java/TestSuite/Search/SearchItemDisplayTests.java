package TestSuite.Search;

import PagesMethods.SearchMethods;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static PagesMethods.SearchMethods.takeElementScreenshot;
import static PagesMethods.SearchMethods.takePageScreenshot;

public class SearchItemDisplayTests extends BaseTest {
    private SearchMethods searchMethods;
    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        searchMethods = new SearchMethods(driver);
    }

    @Test(priority = 1)
    @Description("verify correct items displayed when searching for \"x\"")
    public void searchActualItemsCheck() throws IOException {
        searchMethods.searchForItem(SearchMethods.itemToSearch);

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // get number of all elements from the header
        int numberOfAllElements = searchMethods.extractNumberFromHeader();

        // load more elements
        searchMethods.loadAllElements(numberOfElements, numberOfAllElements);

        // get the elements after new elements loaded
        textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));

        for (WebElement element : textElements) {
            String itemName= element.getText();

            boolean containsExpectedItem = Arrays.stream(SearchMethods.expectedItems)
                    .anyMatch(item -> itemName.contains(item));

            System.out.println(containsExpectedItem);

            // Assert that every item name has a keyword related to the searched word
            try {
                Assert.assertTrue(containsExpectedItem, "Item Name \"" + itemName + "\" is not a correct item as it has no related keyword");
            } catch (AssertionError e) {
                // If the assertion fails, take a screenshot
                takeElementScreenshot(driver, element);
                takePageScreenshot(driver, element);

                throw e;
            }
        }
    }

    @Test(priority = 2)
    @Description("verify no items displayed when searching for a non existing item  ")
    public void searchFictionalItemsCheck() {
        searchMethods.searchForItem("Banana");

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // Assert that no elements are displayed
        Assert.assertEquals(numberOfElements,  0, "There are elements displayed");

        int numberOfItemsINT = searchMethods.extractNumberFromHeader();

        Assert.assertEquals(numberOfItemsINT,  0, "Header says that there are elements displayed");
    }
}
