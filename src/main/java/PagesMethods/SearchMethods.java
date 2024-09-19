package PagesMethods;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchMethods {

    public WebDriver driver ;

    public static String itemToSearch = "jeans";
    public static String[] expectedItems = {"Jeans","jeans","Pant","pant","Pants", "pants"};

    public SearchMethods(WebDriver driver) {this.driver = driver;}

    public WebElement searchForItem (String itemToSearch) {
        WebElement searchButton = driver.findElement(By.cssSelector("svg.icon-search.modal__toggle-open.icon.w-h-22.stroke-w-15"));
        searchButton.click();

        WebElement searchTextBox = driver.findElement(By.cssSelector("input#Search-In-Modal-Menu-Plain"));
        searchTextBox.sendKeys(itemToSearch);

        return searchTextBox ;
    }


}
