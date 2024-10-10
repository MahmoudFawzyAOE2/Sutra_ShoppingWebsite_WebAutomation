package TestSuite.Search;

import Pages.BasicMethods;
import Pages.SearchPage;
import TestData.TestData;
import TestSuite.BaseTest.BaseTest;
import URLs.URLs;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static Pages.BasicMethods.takeElementScreenshot;
import static Pages.BasicMethods.takePageScreenshot;

public class SearchItemDisplayTests extends BaseTest {
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

    @Test(priority = 1)
    @Description("verify correct items displayed when searching for \"x\"")
    public void searchRealItemsCheck() throws IOException, InterruptedException {
        searchMethods.searchForItem(TestData.realProduct);

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // get number of all elements from the header
        int numberOfElements_Header = searchMethods.extractNumberFromHeader();

        // load more elements
        basicMethods.loadAllElements(numberOfElements, numberOfElements_Header);

        // get the elements after new elements loaded
        textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));

        for (WebElement element : textElements) {
            String itemName= element.getText();

            boolean containsExpectedItem = Arrays.stream(TestData.expectedItems)
                    .anyMatch(item -> itemName.contains(item));

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
    public void searchFakeItemsCheck() {
        searchMethods.searchForItem(TestData.fakeProduct);

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // Assert that no elements are displayed
        Assert.assertEquals(numberOfElements,  0, "There are elements displayed");

        int numberOfItemsINT = searchMethods.extractNumberFromHeader();

        Assert.assertEquals(numberOfItemsINT,  0, "Header says that there are elements displayed");
    }
}
