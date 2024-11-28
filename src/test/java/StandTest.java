import config.ProductType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static utils.Actions.*;

public class StandTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "Первый Овощ, VEGETABLE, Овощ, false",
            "Первый экз Овощ, VEGETABLE, Овощ, true",
            "Первый Фрукт, FRUIT, Фрукт, false",
            "Первый экз Фрукт, FRUIT, Фрукт, true",
    })
    public void addVegetablesTest(String name, ProductType type, String expectedType, boolean checkbox)  {
        WebElement addButton = webDriver.findElement(By.xpath("//button[.=\"Добавить\"]"));
        addButton.click();
        assertElementPresent(wait, By.xpath("//body[@class=\"modal-open\"]"), "форма добавления");

        fillForm(webDriver, name, type, checkbox);
        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.=\"Сохранить\"]")));
        saveButton.click();

        By addedElement = By.xpath(String.format("//tr[td[1]='%s' and td[2]='%s' and td[3]='%s']",
                name,
                expectedType,
                checkbox));
        assertElementPresent(wait, addedElement, "добавленный продукт");


        webDriver.findElement(By.xpath("//*[@id=\"navbarDropdown\"]")).click();

        webDriver.findElement(By.xpath("//*[@id=\"reset\"]")).click();

        assertElementNotPresent(webDriver, addedElement, "добавленный продукт");

        try (PreparedStatement pr = connection.prepareStatement("DELETE FROM FOOD WHERE FOOD_NAME like ?")) {
            pr.setString(1, name);
            pr.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}