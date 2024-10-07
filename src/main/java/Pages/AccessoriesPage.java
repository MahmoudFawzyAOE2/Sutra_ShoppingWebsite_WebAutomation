package PagesMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AccessoriesPage {

    private WebDriver driver;

    public AccessoriesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToAccessoriesPage() {
        driver.get("https://sutrastores.com/en/collections/men-accessories");
    }

    public void selectFirstProduct() {
        WebElement firstProduct = driver.findElement(By.cssSelector(".card-title.link-underline.card-title-ellipsis.card-title-change[href='/en/products/hidden-socks-sol-june-2024?variant=45265017929964']"));
        firstProduct.click();
    }

    public void addToCart() {
        WebElement addToCartButton = driver.findElement(By.id("product-add-to-cart"));
        scrollToElement(addToCartButton);
        addToCartButton.click();
    }

    public String getCartCount() {
        WebElement cartCount = driver.findElement(By.xpath("//a[@class='header__icon link header__icon--cart link--text focus-inset cart-icon-bubble']//span[@class='text']"));
        String countText = cartCount.getText();
        return countText;
    }

    public void sortByPriceHighToLow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = driver.findElement(By.cssSelector("div.toolbar-dropdown.filter-sortby"));
        dropdown.click();

        WebElement highToLowOption = driver.findElement(By.xpath("//span[text()='Price, high to low']"));
        highToLowOption.click();
    }

    public List<Double> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(By.cssSelector("span.product-price"));
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replace("LE", "").replace(",", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void filterByPriceRange(int min, int max) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement minPriceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Filter-Min-Price-2")));
        minPriceInput.clear();
        minPriceInput.sendKeys(String.valueOf(min));

        WebElement maxPriceInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Filter-Max-Price-2")));
        maxPriceInput.clear();
        maxPriceInput.sendKeys(String.valueOf(max));

        maxPriceInput.submit();
    }

    public List<Double> getFilteredProductPrices() {
        List<WebElement> priceElements = driver.findElements(By.cssSelector("li .price-item--sale"));
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replace("LE", "").replace(",", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void filterByColor(String color) throws InterruptedException {
        String filterValue;

        if (color.equalsIgnoreCase("Black")) {
            filterValue = "2";
        } else if (color.equalsIgnoreCase("Beige")) {
            filterValue = "1";
        } else {
            throw new IllegalArgumentException("Invalid color: " + color);
        }

        WebElement colorCheckbox = driver.findElement(By.xpath("//label[@for='Filter-Color-" + filterValue + "']//span[@class='pattern']"));
        colorCheckbox.click();
    }


    public List<WebElement> getFilteredProductsByColor(String color) {
        String colorProductXPath = "//span[contains(text(),'- " + color + "')]";
        return driver.findElements(By.xpath(colorProductXPath));
    }

    public void clickFirstProduct() {
        selectFirstProduct();
    }

    public boolean isRedirectedToProductPage(String productName) {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains(productName);
    }

    public void changeViewTo(int columns) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement viewOption = driver.findElement(By.xpath("//div[@class='toolbar-col toolbar-colLeft']//span[@aria-label='Grid 3']"));
        viewOption.click();
        Thread.sleep(2000);
    }

    public void verifyGridItemsPerRow(int expectedColumns) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='main-collection-product-grid']/li")));
        List<WebElement> gridItems = driver.findElements(By.xpath("//ul[@id='main-collection-product-grid']/li"));
        int totalItems = gridItems.size();
        if (totalItems < expectedColumns) {
            throw new AssertionError("Expected at least " + expectedColumns + " items per row, but got " + totalItems);
        }
    }

    public void deselectColor(String color) {
        String filterValue;

        if (color.equalsIgnoreCase("Black")) {
            filterValue = "2";
        } else if (color.equalsIgnoreCase("Beige")) {
            filterValue = "1";
        } else {
            throw new IllegalArgumentException("Invalid color: " + color);
        }

        WebElement colorCheckbox = driver.findElement(By.xpath("//label[@for='Filter-Color-" + filterValue + "']//span[@class='pattern']"));
        colorCheckbox.click();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
