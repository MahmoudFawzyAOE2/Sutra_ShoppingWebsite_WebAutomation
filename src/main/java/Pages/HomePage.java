package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {

    public WebDriver driver ;
    public HomePage(WebDriver driver) {this.driver = driver;}

    public WebElement getCategory(int catNumber) {
        List<WebElement> categoryHeaderList = driver.findElements(By.cssSelector("a.menu-lv-1__action.header__menu-item.list-menu__item.link.focus-inset.menu_mobile_link.link-effect"));
        return categoryHeaderList.get(catNumber);
    }

    public String getElementText(WebElement category) {
        WebElement spanElement = category.findElement(By.className("text"));
        String categoryText = spanElement.getText().toLowerCase();

        if (categoryText.endsWith("s")) {
            categoryText = categoryText.substring(0, categoryText.length() - 1);
        }
        return categoryText;
    }

    public void navigateToCategory(WebElement category) {
        String firstCategoryLink = category.getAttribute("href");
        driver.get(firstCategoryLink);
    }

    public String getHeaderText() {
        WebElement categoryPageHeader = driver.findElement(By.cssSelector("h1.articleLookbook-title.text-left"));
        String categoryPageHeaderText = categoryPageHeader.getText().toLowerCase();
        return categoryPageHeaderText;
    }

    public void loadAccountWidget() throws InterruptedException {
        // locate account button
        WebElement accountButton = driver.findElement(By.cssSelector("svg.icon.icon-account.icon.w-h-24.stroke-w-15"));
        accountButton.click();

        // wait until the account widget loads
        Thread.sleep(5000);
    }





}
