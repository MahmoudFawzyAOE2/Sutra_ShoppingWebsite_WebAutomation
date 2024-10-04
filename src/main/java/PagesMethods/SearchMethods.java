package PagesMethods;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchMethods {

    public WebDriver driver ;

    public static String itemToSearch = "jeans";
    public static String[] expectedItems = {"Jeans","jeans","Pant","pant","Pants", "pants"};
    public static String projectRoot = System.getProperty("user.dir");
    public static String relativePath = "ScreenShots/";
    public static String filePath = projectRoot + File.separator + relativePath;
    public SearchMethods(WebDriver driver) {this.driver = driver;}

    public WebElement searchForItemNoEnter (String itemToSearch) {
        WebElement searchButton = driver.findElement(By.cssSelector("svg.icon-search.modal__toggle-open.icon.w-h-22.stroke-w-15"));
        searchButton.click();

        WebElement searchTextBox = driver.findElement(By.cssSelector("input#Search-In-Modal-Menu-Plain"));
        searchTextBox.sendKeys(itemToSearch);

        return searchTextBox ;
    }

    public void searchForItem (String itemToSearch) {
        WebElement searchButton = driver.findElement(By.cssSelector("svg.icon-search.modal__toggle-open.icon.w-h-22.stroke-w-15"));
        searchButton.click();

        WebElement searchTextBox = driver.findElement(By.cssSelector("input#Search-In-Modal-Menu-Plain"));
        searchTextBox.sendKeys(itemToSearch);
        searchTextBox.sendKeys(Keys.ENTER);
    }

    public int extractNumberFromHeader () {
        // get number of items from the header
        WebElement searchPageHeader = driver.findElement(By.cssSelector("h1.page-header.text-center.scroll-trigger.animate--slide-in"));
        String searchPageHeaderText = searchPageHeader.getText();

        // Extract the numbers using regular expression
        String numberOfItems = searchPageHeaderText.replaceAll("[^0-9]", "");

        // Convert the string to an integer
        int numberOfItemsINT = Integer.parseInt(numberOfItems);
        System.out.println(numberOfItemsINT);

        return numberOfItemsINT ;
    }

    public void loadMoreElements (int numberOfElements) {

        // Scroll Down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000);"); // Scrolls down by 10000 pixels

        // wait for elements to load

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //List<WebElement> elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"), numberOfElements));
    }

    public void loadAllElements (int numberOfElements, int numberOfAllElements) {

        while (numberOfElements < numberOfAllElements){
            loadMoreElements(numberOfElements);

            List<WebElement> textElements = driver.findElements(By.cssSelector("div.collection.resultListing a.card-title.link-underline.card-title-ellipsis.card-title-change"));
            numberOfElements = textElements.size();
        }
    }

    public static void takeElementScreenshot(WebDriver driver, WebElement element) throws IOException {
        // Take a screenshot of the specified element
        File srcFile = element.getScreenshotAs(OutputType.FILE);

        // Move image file to new destination
        String fileName = "element_screenshot_" + System.currentTimeMillis() + ".png";
        File destFile = new File(filePath + fileName);

        // Copy file at destination
        FileUtils.copyFile(srcFile, destFile);

        // Clean up the source file
        srcFile.delete();

        System.out.println("Screenshot saved to: " + destFile.getAbsolutePath());
    }

    public static void takePageScreenshot(WebDriver driver, WebElement element) throws IOException {
        // Scroll to the element
        ((JavascriptExecutor) driver).executeScript(
                "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                        + "var elementTop = arguments[0].getBoundingClientRect().top;"
                        + "window.scrollBy(0, elementTop - (viewPortHeight / 2));",
                element
        );
        // Wait for the scroll to complete
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Take a screenshot of the entire page
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        // Generate unique filename
        String fileName = "page_screenshot_" + System.currentTimeMillis() + ".png";
        File destFile = new File(filePath + fileName);

        // Copy file at destination
        FileUtils.copyFile(srcFile, destFile);

        // Clean up the source file
        srcFile.delete();

        System.out.println("Screenshot saved to: " + destFile.getAbsolutePath());
    }
}