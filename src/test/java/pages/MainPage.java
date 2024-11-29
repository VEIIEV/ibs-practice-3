package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class MainPage {

    private final By AddButtonLocator = By.xpath("//button[.=\"Добавить\"]");

    private WebDriver driver;
    private Wait<WebDriver> wait;

    public MainPage(WebDriver driver, Wait<WebDriver> wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public FormPage openAddForm() {
        driver.findElement(AddButtonLocator).click();
        return new FormPage(driver, wait, this);
    }

    public MainPage resetData(){
        driver.findElement(By.xpath("//*[@id=\"navbarDropdown\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"reset\"]")).click();
        return  this;
    }

    public MainPage assertElementPresent(By locator, String elementName) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception ignored) {
        } finally {
            Assertions.assertNotNull(element, "Элемент { " + elementName + " } не найден на странице!");
        }
        return this;
    }

    public MainPage assertElementNotPresent(By locator, String elementName) {
        WebElement element = null;
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(0));
            element = driver.findElement(locator);
        } catch (Exception ignored) {
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
            Assertions.assertNull(element, "Элемент { " + elementName + " } присутствует на странице!");
        }
        return this;
    }

}
