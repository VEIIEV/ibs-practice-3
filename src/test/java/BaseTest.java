import config.DbConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FormPage;
import pages.MainPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;

public abstract class BaseTest {

    protected static WebDriver webDriver;
    protected static Connection connection;
    protected static Wait<WebDriver> wait;
    protected static String name;
    protected static MainPage mainPage;
    protected static FormPage formPage;

    @BeforeAll
    public static void beforeAll() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        webDriver.get("http://localhost:8080/food");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        connection = DbConfig.getConnection();
        mainPage = new MainPage(webDriver, wait);
        formPage = new FormPage(webDriver, wait, mainPage);
    }


    @AfterEach
    public void afterEach() {
        try (PreparedStatement pr = connection.prepareStatement("DELETE FROM FOOD WHERE FOOD_NAME like ?")) {
            pr.setString(1, name);
            pr.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void afterAll() {
        DbConfig.closeConnection(connection);
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
