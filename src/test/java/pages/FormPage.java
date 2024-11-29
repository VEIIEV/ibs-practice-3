package pages;

import config.ProductType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormPage {

    private WebDriver webDriver;
    private MainPage mainPage;
    private Wait<WebDriver> wait;

    private final By namePlateLocator = By.xpath("//input[@placeholder=\"Наименование\"]");
    private final By typeSelectorLocator = By.id("type");
    private final By exoticCheckboxLocator = By.id("exotic");

    public FormPage(WebDriver driver, Wait<WebDriver> wait, MainPage mainPage) {
        webDriver = driver;
        this.wait = wait;
        this.mainPage = mainPage;

    }


    public MainPage fillForm(String name, ProductType type, boolean checkbox) {
        webDriver.findElement(namePlateLocator).sendKeys(name);
        new Select(webDriver.findElement(typeSelectorLocator)).selectByValue(type.toString());
        if (checkbox) {
            webDriver.findElement(exoticCheckboxLocator).click();
        }
        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.=\"Сохранить\"]")));
        saveButton.click();
        return mainPage;

    }

}
