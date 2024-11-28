package utils;

import config.ProductType;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class Actions {


    public static void fillForm(WebDriver webDriver, String name, ProductType type, boolean checkbox) {
        webDriver.findElement(By.xpath("//input[@placeholder=\"Наименование\"]")).sendKeys(name);
        new Select(webDriver.findElement(By.id("type"))).selectByValue(type.toString());
        if (checkbox) {
            webDriver.findElement(By.id("exotic")).click();
        }

    }

    public static void assertElementPresent(Wait<WebDriver> wait, By locator, String elementName) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception ignored) {
        } finally {
            Assertions.assertNotNull(element, "Элемент { " + elementName + " } не найден на странице!");
        }
    }

    public static void assertElementNotPresent(WebDriver webDriver, By locator, String elementName) {
        WebElement element = null;
        try {
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            element = webDriver.findElement(locator);
        } catch (Exception ignored) {
        } finally {
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
            Assertions.assertNull(element, "Элемент { " + elementName + " } не найден на странице!");
        }
    }
}
