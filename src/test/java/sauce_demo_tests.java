import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class sauce_demo_tests extends  base_test_sauce_demo{

    @Test
    public void Ordering_Filter_Price_High_To_Low_Test()
    {

        WebElement sortComboBox = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("product_sort_container")));

        Select selectObject = new Select(sortComboBox);
        selectObject.selectByVisibleText("Price (high to low)");

        //Verification
        List<WebElement> productPrices = driver.findElements(By.className("inventory_item_price"));

        List<Double> actualProductsOrder = new ArrayList<>();

        for (WebElement element : productPrices) {
            String priceText = element.getText().replace("$", "");
            actualProductsOrder.add(Double.parseDouble(priceText));
        }

        // Verify if the list is sorted in descending order
        boolean isSorted = Ordering.natural().reverse().isOrdered(actualProductsOrder);
        Assertions.assertTrue(isSorted);
    }

    @Test
    public void checkLinkFacebook () throws InterruptedException {

        WebElement linkFacebook = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Facebook")));
        String expectedUrl = "https://www.facebook.com/saucelabs";
        String actualUrl = linkFacebook.getAttribute("href");
        Assertions.assertEquals(actualUrl, expectedUrl);
        linkFacebook.click();

        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, expectedUrl);
    }

    @Test
    public void checkSubTotalPriceOfProducts()
    {
        WebElement buttonAddSauceLabsBikeLight = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("add-to-cart-sauce-labs-bike-light")));
        buttonAddSauceLabsBikeLight.click();

        WebElement buttonAddSauceLabsFleeceJacket = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        buttonAddSauceLabsFleeceJacket.click();

        WebElement linkCarritoCompra = driver.findElement(By.className("shopping_cart_link"));
        linkCarritoCompra.click();

        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        checkoutButton.click();

        WebElement firstNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        firstNameTextBox.sendKeys("Nicolas");

        WebElement lastNameTextBox = driver.findElement(By.id("last-name"));
        lastNameTextBox.sendKeys("Moscoso");

        WebElement postalCodeTextBox = driver.findElement(By.id("postal-code"));
        postalCodeTextBox.sendKeys("0000");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        WebElement itemTotalText = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("summary_subtotal_label")));
        String textoItemTotal = itemTotalText.getText();
        System.out.println(textoItemTotal);

        // FALLA
        Assertions.assertEquals(textoItemTotal, "Item total: $59.98");
    }

    @Test
    public void checkLogoutButton() throws InterruptedException {
        WebElement menuButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("react-burger-menu-btn")));
        menuButton.click();

        Thread.sleep(5000);

        WebElement logoutButtonMenu = driver.findElement(By.id("logout_sidebar_link"));
        logoutButtonMenu.click();

        WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login-button")));

        Assertions.assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void buyProductsSuccessfully()
    {
        WebElement buttonAddSauceLabsBikeLight = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("add-to-cart-sauce-labs-bike-light")));
        buttonAddSauceLabsBikeLight.click();

        WebElement buttonAddSauceLabsBackpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        buttonAddSauceLabsBackpack.click();

        WebElement linkCarritoCompra = driver.findElement(By.className("shopping_cart_link"));
        linkCarritoCompra.click();

        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("checkout")));
        checkoutButton.click();

        WebElement firstNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        firstNameTextBox.sendKeys("Nicolas");

        WebElement lastNameTextBox = driver.findElement(By.id("last-name"));
        lastNameTextBox.sendKeys("Moscoso");

        WebElement postalCodeTextBox = driver.findElement(By.id("postal-code"));
        postalCodeTextBox.sendKeys("0000");

        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();

        WebElement finishPurchaseButton = driver.findElement(By.id("finish"));
        finishPurchaseButton.click();

        WebElement successfullyPurchaseImage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("pony_express")));
        Assertions.assertTrue(successfullyPurchaseImage.isDisplayed());
    }
}
