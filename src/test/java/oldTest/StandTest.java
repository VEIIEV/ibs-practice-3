package oldTest;

import config.ProductType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;


@Disabled("old tests")
public class StandTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "Первый Овощ, VEGETABLE, Овощ, false",
            "Первый экз Овощ, VEGETABLE, Овощ, true",
            "Первый Фрукт, FRUIT, Фрукт, false",
            "Первый экз Фрукт, FRUIT, Фрукт, true",
    })
    public void addVegetablesTest(String name, ProductType type, String expectedType, boolean checkbox) {
        BaseTest.name = name;
        By addedElement = By.xpath(String.format("//tr[td[1]='%s' and td[2]='%s' and td[3]='%s']",
                name,
                expectedType,
                checkbox));

        mainPage.openAddForm()
                .fillForm(name, type, checkbox)
                .assertElementPresent(addedElement, "добавленный продукт")
                .resetData()
                .assertElementNotPresent(addedElement, "добавленный продукт");
//        WebElement addButton = webDriver.findElement(By.xpath("//button[.=\"Добавить\"]"));
//        addButton.click();
//        assertElementPresent(wait, By.xpath("//body[@class=\"modal-open\"]"), "форма добавления");
//
//        fillForm(webDriver, name, type, checkbox);
//        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.=\"Сохранить\"]")));
//        saveButton.click();
//        assertElementPresent(wait, addedElement, "добавленный продукт");
//
//
//        webDriver.findElement(By.xpath("//*[@id=\"navbarDropdown\"]")).click();
//
//        webDriver.findElement(By.xpath("//*[@id=\"reset\"]")).click();
//
//        assertElementNotPresent(webDriver, addedElement, "добавленный продукт");

    }

}