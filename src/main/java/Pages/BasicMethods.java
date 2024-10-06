package Pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasicMethods {

    public WebDriver driver ;
    public BasicMethods(WebDriver driver) {this.driver = driver;}
    public static String filePath = System.getProperty("user.dir") + File.separator + "ScreenShots/";

    public void loadMoreElements (int numberOfElements) throws InterruptedException {

        // Scroll Down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 1000);"); // Scrolls down by 10000 pixels

        // wait for elements to load
        System.out.println("Loading...");
        TimeUnit.SECONDS.sleep(1);
    }

    public void loadAllElements (int numberOfElements, int numberOfAllElements) throws InterruptedException {

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
        // Scroll to the element (make the element in the middle of the page)
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

