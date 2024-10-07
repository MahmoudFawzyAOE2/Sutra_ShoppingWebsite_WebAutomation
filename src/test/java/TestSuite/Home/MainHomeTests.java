package TestSuite.Home;

import Pages.BasicMethods;
import Pages.HomePage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class MainHomeTests extends BaseTest {

    private HomePage homePage;
    private BasicMethods basicMethods;

    @BeforeMethod
    public void setUp() {
        // Connect WebDriver from BaseTest with WebDriver from SearchMethods
        basicMethods = new BasicMethods(driver);
        homePage = new HomePage(driver);

        // Go to the main page
        driver.get(mainURL);
    }

    @Test(priority = 1)
    @Description("verify category navigation")
    public void homeCategoryNav() {

        // locate the category
        List<WebElement> categoryHeaderList = driver.findElements(By.cssSelector("a.menu-lv-1__action.header__menu-item.list-menu__item.link.focus-inset.menu_mobile_link.link-effect"));
        WebElement category = categoryHeaderList.get(0);

        // get the category name
        WebElement spanElement = category.findElement(By.className("text"));

        String categoryText = spanElement.getText().toLowerCase();

        if (categoryText.endsWith("s")) {
            categoryText = categoryText.substring(0, categoryText.length() - 1);
        }

        // navigate to the category page
        String firstCategoryLink = category.getAttribute("href");
        driver.get(firstCategoryLink);

        // extract text from header
        WebElement categoryPageHeader = driver.findElement(By.cssSelector("h1.articleLookbook-title.text-left"));
        String categoryPageHeaderText = categoryPageHeader.getText().toLowerCase();

        Assert.assertTrue(categoryPageHeaderText.contains(categoryText), "Text Mismatch.");
    }

    @Test(priority = 2)
    @Description("verify sub-category navigation")
    public void homeSubCategoryNav() {

        // locate category
        List<WebElement> categoryHeaderList = driver.findElements(By.cssSelector("a.menu-lv-1__action.header__menu-item.list-menu__item.link.focus-inset.menu_mobile_link.link-effect"));
        WebElement category = categoryHeaderList.get(1);

        // locate sub-category element
        WebElement subCategory = category.findElement(By.xpath("./following-sibling::ul/li[2]/a"));

        WebElement subCategorySpan = subCategory.findElement(By.cssSelector("span.text.p-relative"));

        String subCategoryText = subCategorySpan.getText();

        System.out.println("subCategoryText: " + subCategoryText);

        /*

        String subCategoryLink = subCategory.getAttribute("href");

        if (subCategoryText.endsWith("s")) {
            subCategoryText = subCategoryText.substring(0, subCategoryText.length() - 1);
        }

        // navigate to the category page
        driver.get(subCategoryLink);

        // extract text from header
        WebElement categoryPageHeader = driver.findElement(By.cssSelector("h1.articleLookbook-title.text-left"));
        String categoryPageHeaderText = categoryPageHeader.getText().toLowerCase();

        System.out.println(categoryPageHeaderText);
        System.out.println(subCategoryText);
        Assert.assertTrue(categoryPageHeaderText.contains(subCategoryText), "Text Mismatch.");

         */
    }
}
