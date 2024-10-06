package TestSuite.Search;

import Pages.BasicMethods;
import Pages.SearchPage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SearchItemNumberTests extends BaseTest {

    private SearchPage searchMethods;
    private BasicMethods basicMethods;

    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        basicMethods = new BasicMethods(driver);
        searchMethods = new SearchPage(driver);

        // Go to the main page
        driver.get(mainURL);
    }

    @Test(priority = 1)
    @Description("verify correct number of search results on result page ")
    public void searchNumOfItems() throws InterruptedException {

        searchMethods.searchForItem(SearchPage.itemToSearch);

        /* 2) get number of items from the header */
        int numberOfElements_Header = searchMethods.extractNumberFromHeader();/* 1) get number of items by counting elements */

        // get number of elements
        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        int numberOfElements = textElements.size();

        // load All elements
        basicMethods.loadAllElements(numberOfElements, numberOfElements_Header);

        // get number of elements after loading
        textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
        numberOfElements = textElements.size();

        Assert.assertEquals(numberOfElements,  numberOfElements_Header, "Number of Items are not the same");
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
