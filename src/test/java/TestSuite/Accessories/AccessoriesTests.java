package TestSuite.Accessories;

import Pages.AccessoriesPage;
import TestSuite.BaseTest.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AccessoriesTests extends BaseTest {

    private AccessoriesPage accessoriesPage;

    @BeforeMethod
    public void setUp() {
        accessoriesPage = new AccessoriesPage(driver);
        accessoriesPage.navigateToAccessoriesPage();
    }

    @Test(priority = 1, description = "Verify Add to Cart functionality")
    public void verifyAddToCart() throws InterruptedException {
        accessoriesPage.selectFirstProduct();
        accessoriesPage.addToCart();
        Thread.sleep(2000);

        String countText = accessoriesPage.getCartCount();
        assertTrue(Integer.parseInt(countText) > 0, "Product was not added to the cart.");
    }

    @Test(priority = 2, description = "Verify sorting options (Price: High to Low)")
    public void verifySortingOptions() {
        accessoriesPage.sortByPriceHighToLow();

        List<Double> prices = accessoriesPage.getProductPrices();
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices, Collections.reverseOrder());

        assertEquals(prices, sortedPrices, "The products are not sorted from high to low!");
    }

    @Test(priority = 3, description = "Verify price filtering between 50 and 500 LE")
    public void verifyPriceFilteringOptions() {
        accessoriesPage.filterByPriceRange(50, 500);

        List<Double> prices = accessoriesPage.getFilteredProductPrices();
        for (Double price : prices) {
            assertTrue(price >= 50.0 && price <= 500.0, "Price is out of range: " + price);
        }
    }

    @Test(priority = 4, description = "Verify filtering by black color")
    public void verifyBlackColorFilteringOption() throws InterruptedException {
        accessoriesPage.filterByColor("Black");

        List<WebElement> blackProducts = accessoriesPage.getFilteredProductsByColor("Black");
        assertTrue(blackProducts.size() >= 5, "Less than 3 black products found.");
    }

    @Test(priority = 5, description = "Verify correct redirect to product page")
    public void verifyCorrectRedirect() {
        accessoriesPage.clickFirstProduct();
        assertTrue(accessoriesPage.isRedirectedToProductPage("hidden-socks-sol-june-2024"),
                "Did not navigate to the correct product page.");
    }

    @Test(priority = 6, description = "Verify 'View as' options for product grid")
    public void verifyViewAsOptions() throws InterruptedException {
        accessoriesPage.changeViewTo(3);  // 3 columns grid
        accessoriesPage.verifyGridItemsPerRow(3);

        accessoriesPage.changeViewTo(2);  // 2 columns grid
        accessoriesPage.verifyGridItemsPerRow(2);

        accessoriesPage.changeViewTo(1);  // List view
        accessoriesPage.verifyGridItemsPerRow(1);
    }

    @Test(priority = 7, description = "Verify price filtering between 0 and 40 LE")
    public void verifyInvalidPriceFilteringOptions() {
        accessoriesPage.filterByPriceRange(0, 40);

        List<Double> prices = accessoriesPage.getFilteredProductPrices();
        assertTrue(prices.isEmpty(), "Some prices are within the range, but expected none.");
    }

    @Test(priority = 8, description = "Verify filtering by Beige and Black colors")
    public void verifyMixedColorsFilteringOption() throws InterruptedException {
        accessoriesPage.filterByColor("Beige");

        List<WebElement> beigeProducts = accessoriesPage.getFilteredProductsByColor("Beige");
        assertTrue(beigeProducts.size() > 0, "No beige products found.");

        accessoriesPage.filterByColor("Black");

        List<WebElement> blackProducts = accessoriesPage.getFilteredProductsByColor("Black");
        assertTrue(blackProducts.size() > 0, "No black products found after selecting both Beige and Black filters.");

        accessoriesPage.deselectColor("Beige");
        Thread.sleep(3000);
        beigeProducts = accessoriesPage.getFilteredProductsByColor("Beige");
        assertTrue(beigeProducts.isEmpty(), "Beige products found after deselecting Beige filter.");
    }
    @Test(priority = 1, description = "Verify Add to Cart functionality")
    public void verifyRemoveFromCart() throws InterruptedException {
        accessoriesPage.selectFirstProduct();
        accessoriesPage.addToCart();
        Thread.sleep(2000);
        accessoriesPage.removeFromCart();
        Thread.sleep(2000);
        String countText = accessoriesPage.getCartCount();
        assertTrue(Integer.parseInt(countText) == 0, "Product was not removed from the cart.");
    }


}
