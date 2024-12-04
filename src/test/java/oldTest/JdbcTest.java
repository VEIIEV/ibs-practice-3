package oldTest;

import config.ProductType;
import daos.FoodDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JdbcTest extends BaseTest {

    protected static FoodDAO foodDAO;

    @BeforeAll
    public static void intDao() {
        foodDAO = new FoodDAO(connection);
    }

    @ParameterizedTest
    @CsvSource({"КОРТОХА, VEGETABLE, false"})
    @DisplayName("Проверка добавления нового товара в таблицу")
    public void addNewProductTest(String name, ProductType type, boolean checkbox) throws InterruptedException {
        BaseTest.name = name;
        int defaultAmount = foodDAO.count();

        mainPage.openAddForm()
                .fillForm(name, type, checkbox);
        Thread.sleep(3000);
        Assertions.assertEquals(defaultAmount + 1,
                foodDAO.count(),
                "Число продуктов в таблице не соответсвует ожиданиям");

        Assertions.assertEquals(name + " " + type + " " + checkbox,
                foodDAO.getFoodByName(name),
                "продукт в БД отличается от добавленного ранее");
        foodDAO.deleteByName(name);
        Assertions.assertEquals(defaultAmount,
                foodDAO.count(),
                "Число продуктов в таблице не соответсвует ожиданиям");


    }

    @ParameterizedTest
    @CsvSource({"Яблоко, FRUIT, false"})
    @DisplayName("Проверка добавления дубликата товара")
    public void addDuplicateProductTest(String name, ProductType type, boolean checkbox) throws InterruptedException {
        BaseTest.name = name;
        int defaultAmount = foodDAO.count();
        mainPage.openAddForm()
                .fillForm(name, type, checkbox);
        Thread.sleep(3000);
        Assertions.assertEquals(defaultAmount,
                foodDAO.count(),
                "Число продуктов в таблице увеличилось, дубликат был добавлен");
    }


}
