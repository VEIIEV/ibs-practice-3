package cucumber.steps;

import config.ProductType;
import cucumber.ScenarioContext;
import daos.FoodDAO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;


public class SecondPractice {

    ScenarioContext scenarioContext = ScenarioContext.getContext();
    FoodDAO foodDAO = ScenarioContext.getFoodDAO();
    int defaultAmount;

    @Когда("^пользователь добавляет \"(?:новый|существующий)\" продукт$")
    public void userAddProduct(DataTable dataTable) {
        scenarioContext.setName(dataTable.row(1).get(0));
        defaultAmount = foodDAO.count();
        scenarioContext.getMainPage().openAddForm()
                .fillForm(dataTable.row(1).get(0),
                        ProductType.valueOf(dataTable.row(1).get(1)),
                        Boolean.parseBoolean(dataTable.row(1).get(0)));
    }

    @Тогда("продукт  {string} добавляется в таблицу")
    public void productAddedInDb(String name) throws InterruptedException {
        Thread.sleep(3000);
        Assertions.assertEquals(defaultAmount + 1,
                foodDAO.count(),
                "Число продуктов в таблице не соответсвует ожиданиям");


    }

    @Тогда("продукт  {string} не добавляется в таблицу")
    public void productWontAddInDB(String name) throws InterruptedException {
        Thread.sleep(3000);
        Assertions.assertEquals(defaultAmount,
                foodDAO.count(),
                "Число продуктов в таблице увеличилось, дубликат был добавлен");
    }

    @Затем("сбросить данные в таблице")
    public void resetDataInDB() {
        foodDAO.deleteByName(scenarioContext.getName());
        Assertions.assertEquals(defaultAmount,
                foodDAO.count(),
                "Число продуктов в таблице не соответсвует ожиданиям");
    }
}
