package TestSuite.Search;

import PagesMethods.SearchMethods;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

        searchMethods.searchForItem(SearchMethods.itemToSearch);

        /* 1) get number of items by counting elements */
        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // load more elements
        searchMethods.loadMoreElements(numberOfElements);

        // get number of elements after loading
        textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        numberOfElements = textElements.size();
        System.out.println(numberOfElements);

        /* 2) get number of items from the header */
        int numberOfItemsINT = searchMethods.extractNumberFromHeader();

        Assert.assertEquals(numberOfElements,  numberOfItemsINT, "Number of Items are not the same");
    }

    @Test(priority = 2)
    @Description("verify that No search can occur without text to be inserted")
    public void searchNumOfItemsZero() {

        // Search for nothing
        searchMethods.searchForItem("");

        // verify that url didn't change
        Assert.assertEquals(driver.getCurrentUrl(), "https://sutrastores.com/en");
    }
}
