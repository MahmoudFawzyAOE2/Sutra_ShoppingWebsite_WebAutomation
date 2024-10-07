package TestSuite.Home;

import Pages.BasicMethods;
import Pages.HomePage;
import TestSuite.BaseTest.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HeaderCategoriesTests extends BaseTest {

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
        WebElement category = homePage.getCategory(0);

        // get the category name
        String categoryText = homePage.getElementText(category);

        // navigate to the category page
        homePage.navigateToCategory(category);

        // extract text from header
        String categoryPageHeaderText =  homePage.getHeaderText();

        System.out.println(categoryPageHeaderText);
        System.out.println(categoryText);
        Assert.assertTrue(categoryPageHeaderText.contains(categoryText), "Text Mismatch.");
    }

    @Test(priority = 2)
    @Description("verify sub-category navigation")
    public void homeSubCategoryNav() {

        // locate category
        WebElement category = homePage.getCategory(1);

        // locate sub-category element
        WebElement subCategory = category.findElement(By.xpath("./following-sibling::ul/li[2]/a"));

        Actions actions = new Actions(driver);
        actions.moveToElement(category).perform();

        // get sub-category name
        String subCategoryText = homePage.getElementText(category);

        // navigate to the category page
        homePage.navigateToCategory(subCategory);

        // extract text from header
        String subCategoryPageHeaderText =  homePage.getHeaderText();

        System.out.println(subCategoryPageHeaderText);
        System.out.println(subCategoryText);
        Assert.assertTrue(subCategoryPageHeaderText.contains(subCategoryText), "Text Mismatch.");


    }
}
