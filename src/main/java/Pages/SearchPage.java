package Pages;


import org.openqa.selenium.*;

public class SearchPage {
    public WebDriver driver;
    public SearchPage(WebDriver driver) {this.driver = driver;}
    public static String itemToSearch = "jeans";
    public static String[] expectedItems = {"Jeans", "jeans", "Pant", "pant", "Pants", "pants"};

    public WebElement searchForItemNoEnter(String itemToSearch) {
        WebElement searchButton = driver.findElement(By.cssSelector("svg.icon-search.modal__toggle-open.icon.w-h-22.stroke-w-15"));
        searchButton.click();

        WebElement searchTextBox = driver.findElement(By.cssSelector("input#Search-In-Modal-Menu-Plain"));
        searchTextBox.sendKeys(itemToSearch);

        return searchTextBox;
    }

    public void searchForItem(String itemToSearch) {
        WebElement searchButton = driver.findElement(By.cssSelector("svg.icon-search.modal__toggle-open.icon.w-h-22.stroke-w-15"));
        searchButton.click();

        WebElement searchTextBox = driver.findElement(By.cssSelector("input#Search-In-Modal-Menu-Plain"));
        searchTextBox.sendKeys(itemToSearch);
        searchTextBox.sendKeys(Keys.ENTER);
    }

    public int extractNumberFromHeader() {
        // get number of items from the header
        WebElement searchPageHeader = driver.findElement(By.cssSelector("h1.page-header.text-center.scroll-trigger.animate--slide-in"));
        String searchPageHeaderText = searchPageHeader.getText();

        // Extract the numbers using regular expression
        String numberOfItems = searchPageHeaderText.replaceAll("[^0-9]", "");

        // Convert the string to an integer
        int numberOfItemsINT = Integer.parseInt(numberOfItems);
        System.out.println(numberOfItemsINT);

        return numberOfItemsINT;
    }

}
