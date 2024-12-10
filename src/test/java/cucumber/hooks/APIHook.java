package cucumber.hooks;

import cucumber.ApiScenarioContext;
import io.cucumber.java.Before;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Header;

public class APIHook {


    @Step("Сброс данных перед тестом")
    @Before("@API")
    public void resetTestData() {

        RestAssured.given()
                .baseUri("http://localhost:8080")
                .basePath("/api/data/reset")
                .header(new Header("accept", "*/*"))
                .when()
                .post()
                .then()
//                .log().all()
                .statusCode(200);
    }

    @Step("Сброс данных после теста")
    @Before("@API")
    public void resetTestDataAfterTest() {
        RestAssured.given()
                .baseUri("http://localhost:8080")
                .basePath("/api/data/reset")
                .header(new Header("accept", "*/*"))
                .when()
                .post()
                .then()
//                .log().all()
                .statusCode(200);
        ApiScenarioContext.clearContext();

    }


}
