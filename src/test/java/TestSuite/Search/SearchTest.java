package TestSuite.Search;

import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

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
    @Description("verify Search functionality at the header ")
    public void searchItem() {

        WebElement searchButton = driver.findElement(By.cssSelector("svg.icon-search.modal__toggle-open.icon.w-h-22.stroke-w-15"));
        searchButton.click();

        WebElement searchTextBox = driver.findElement(By.cssSelector("input#Search-In-Modal-Menu-Plain"));
        searchTextBox.sendKeys("jeans");

        WebElement searchConfirmButton = driver.findElement(By.cssSelector("svg.icon-search-1.p-relative"));
        searchConfirmButton.click();

        String expectedResult = "https://sutrastores.com/en/search?q=jeans&options%5Bprefix%5D=last&type=product";
        String actualResult = driver.getCurrentUrl();

        Assert.assertEquals(actualResult,  expectedResult);

    }
}
