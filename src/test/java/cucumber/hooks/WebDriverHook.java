package cucumber.hooks;

import config.DbConfig;
import config.ProjectConfig;
import cucumber.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.Пусть;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WebDriverHook {
    ScenarioContext scenarioContext;

    @Step("Инициализация подключения к БД")
    @BeforeAll
    public static void initConnection() {
        ScenarioContext.setConnection(DbConfig.getConnection());
    }

    @Step("Инициализация контекста и настройка Selenium WebDriver")
    @Before(value = "@JDBC or @UI", order = 10)
    public void webDriverInit() {
        scenarioContext = ScenarioContext.getContext();
        scenarioContext.setWebDriver(new ChromeDriver());
    }

    @Step("Открытие базовой страницы")
    @Before(value = "@JDBC or @UI", order = 20)
    @Пусть("Открыта страница страница со списком товаров")
    public void открытаСтраницаСтраницаСоСпискомТоваров() {
        scenarioContext.getWebDriver().get(ProjectConfig.getBaseUrl()+"/food");

    }


    @Step("Закрытие WebDriver")
    @After(value = "@JDBC or @UI", order = 30)
    public void closeWebDriver() {
        if (scenarioContext.getWebDriver() != null) {
            scenarioContext.getWebDriver().quit();
        }
    }


    @Step("очистка тестовых данных")
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

    @Step("Очистка контекста тестового сценария")
    @After(value = "@JDBC or @UI", order = 10)
    public void clearContext() {
        ScenarioContext.clearContext();
    }

    @Step("Закрытие соединения с БД")
    @AfterAll
    public static void afterAll() {
        DbConfig.closeConnection(ScenarioContext.getConnection());
    }


}
