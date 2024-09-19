package TestSuite.Search;

import PagesMethods.SearchMethods;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

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
    @Description("verify correct items displayed when shearching for \"x\"")
    public void searchItemsCheck() {

        WebElement searchTextBox = searchMethods.searchForItem(SearchMethods.itemToSearch);
        searchTextBox.sendKeys(Keys.ENTER);

        /*
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 10000);"); // Scrolls down by 10000 pixels
         */

        List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));

        for (WebElement element : textElements) {
            String wholeText = element.getText();

            System.out.println(wholeText);

            Assert.assertTrue(Arrays.stream(SearchMethods.expectedItems)
                    .anyMatch(item -> wholeText.contains(item)));

        }

    }
}
