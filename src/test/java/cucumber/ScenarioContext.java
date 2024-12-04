package cucumber;

import daos.FoodDAO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;
import pages.MainPage;

import java.sql.Connection;
import java.time.Duration;

public class ScenarioContext {

    private static ScenarioContext context;
    private static Connection connection;

    private WebDriver webDriver;
    private Wait<WebDriver> wait;
    private MainPage mainPage;
    private FormPage formPage;
    private String name = "";

    private ScenarioContext() {
    }

    public static ScenarioContext getContext() {
        if (context == null) {
            context = new ScenarioContext(); // Создаём экземпляр при первом вызове
        }
        return context;

    }

    public static void clearContext() {
        context = null;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public Wait<WebDriver> getWait() {
        return wait;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public FormPage getFormPage() {
        return formPage;
    }

    public String getName() {
        return name;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static FoodDAO getFoodDAO() {
        return new FoodDAO(connection);
    }

    public ScenarioContext setName(String name) {
        this.name = name;
        return this;
    }

    public static void setConnection(Connection connection) {
        ScenarioContext.connection = connection;
    }

    public ScenarioContext setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(3));
        mainPage = new MainPage(this.webDriver, wait);
        formPage = new FormPage(this.webDriver, wait, mainPage);
        return this;
    }
}
