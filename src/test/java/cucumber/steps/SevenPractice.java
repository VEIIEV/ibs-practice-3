package cucumber.steps;

import cucumber.ApiScenarioContext;
import entities.Product;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;

import java.util.List;


@Owner("BotkinKA")
@Story("Добавление продуктов в таблицу с помощью API")
public class SevenPractice {

    ApiScenarioContext scenarioContext = ApiScenarioContext.getContext();

//    сейвить куки сессии после первого запроса, использовать его дальше, чистить потом


    @SneakyThrows
    @Step("Добавление тестовых данных в таблицу")
    @Когда(value = "^пользователь добавляет \"(новый|существующий)\" продукт \\(API\\)$")
    public void userAddProductViaAPI(String isNew, DataTable dataTable) {

        Response response = getAllProductResponse()
                .statusCode(200)
                .extract()
                .response();

        List<String> existedName = response.jsonPath().getList("name");
        scenarioContext.setSessionCookie(response.getCookie("JSESSIONID"));

        if (isNew.equals("новый")) {
            Assertions.assertFalse(existedName.contains(dataTable.row(1).get(0)), "Таблица уже содержит введенный товар");
        } else {
            Assertions.assertTrue(existedName.contains(dataTable.row(1).get(0)), "Таблица не содержит введенный товар");
        }

        Product product = new Product(dataTable.row(1).get(0),
                dataTable.row(1).get(1),
                Boolean.parseBoolean(dataTable.row(1).get(2)));
        RestAssured.given()
                .baseUri("http://localhost:8080")
                .headers(scenarioContext.getBaseHeaders())
                .header("Content-Type", "application/json")
                .cookie("JSESSIONID", scenarioContext.getSessionCookie())
                .when()
                .body(product)
                .post("/api/food")
                .then();

    }

    @Step("Проверка успешности добавления НОВОГО товара")
    @Тогда("продукт  {string} добавляется в таблицу \\(API)")
    public void productAddedInDbViaAPI(String name) {
        List<String> existedName = getAllProductResponse()
                .extract()
                .jsonPath().getList("name");
        Assertions.assertTrue(existedName.contains(name), "Товар не добавился в таблицу");

    }



    @Step("Проверка провала добавления ДУБЛИКАТА")
    @Тогда("продукт  {string} не добавляется в таблицу \\(API)")
    public void productWontAddInDBViaAPI(String name) {
        List<String> existedName = getAllProductResponse()
                .statusCode(200)
                .extract()
                .jsonPath().getList("name");
        long counter = existedName.stream().filter(i -> i.equals(name)).count();

        Assertions.assertEquals(1, counter, "В таблицу добавлен Дубликат");
    }

    @Step("сброс тестовых данных, при успешном (ожидаемом) добавление товара")
    @Затем("сбросить данные в таблице \\(API)")
    public void resetDataInDBViaAPI() {
        RestAssured.given()
                .baseUri("http://localhost:8080")
                .basePath("/api/data/reset")
                .headers(scenarioContext.getBaseHeaders())
                .cookie("JSESSIONID", scenarioContext.getSessionCookie())
                .when()
                .post()
                .then()
//                .log().all()
                .statusCode(200);

    }


    private ValidatableResponse getAllProductResponse() {
        return RestAssured.given()
                .baseUri("http://localhost:8080")
                .headers(scenarioContext.getBaseHeaders())
                .cookie("JSESSIONID", scenarioContext.getSessionCookie())
                .when()
                .get("/api/food").then();
    }


}
