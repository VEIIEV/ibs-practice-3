import config.DbConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;

public abstract class BaseTest {

    protected static WebDriver webDriver;
    protected static Connection connection;
    protected static Wait<WebDriver> wait;

    @BeforeAll
    public static void beforeAll() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        webDriver.get("http://localhost:8080/food");
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        connection = DbConfig.getConnection();
    }



    @AfterAll
    public static void afterAll() {
        DbConfig.closeConnection(connection);
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
