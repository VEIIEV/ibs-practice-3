package cucumber.hooks;

import config.DbConfig;
import cucumber.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.Пусть;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WebDriverHook {
    ScenarioContext scenarioContext;

    @BeforeAll
    public static void initConnection() {
        ScenarioContext.setConnection(DbConfig.getConnection());
    }

    @Before(value = "@JDBC or @UI", order = 10)
    public void webDriverInit() {
        scenarioContext = ScenarioContext.getContext();
        scenarioContext.setWebDriver(new ChromeDriver());
    }

    @Before(value = "@JDBC or @UI", order = 20)
    @Пусть("Открыта страница страница со списком товаров")
    public void открытаСтраницаСтраницаСоСпискомТоваров() {
        scenarioContext.getWebDriver().get("http://localhost:8080/food");

    }


    @After(value = "@JDBC or @UI", order = 10)
    public void closeWebDriver() {
        if (scenarioContext.getWebDriver() != null) {
            scenarioContext.getWebDriver().quit();
        }
    }


    @After(value = "@JDBC or @UI", order = 20)
    public void restoreDB() {
        try (PreparedStatement pr = ScenarioContext.getConnection().prepareStatement(
                "DELETE FROM FOOD " +
                        "WHERE FOOD_ID = ( " +
                        "    SELECT MAX(FOOD_ID) " +
                        "    FROM FOOD " +
                        "    WHERE FOOD_NAME = ? " +
                        ")"
        )) {
            pr.setString(1, scenarioContext.getName());
            pr.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @After(value = "@JDBC or @UI", order = 30)
    public void clearContext() {
        ScenarioContext.clearContext();
    }

    @AfterAll
    public static void afterAll() {
        DbConfig.closeConnection(ScenarioContext.getConnection());
    }


}
