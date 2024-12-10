package cucumber;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class ApiScenarioContext {

    private static ApiScenarioContext context;
    private final Headers baseHeaders = Headers.headers(
            new Header("Cache-Control", "no-cache"),
            new Header("Accept", "*/*"));
    private String sessionCookie;

    private ApiScenarioContext() {
    }

    public static ApiScenarioContext getContext() {
        if (context == null) {
            context = new ApiScenarioContext(); // Создаём экземпляр при первом вызове
        }
        return context;
    }

    public static void clearContext() {
        context = null;
    }

    public Headers getBaseHeaders() {
        return baseHeaders;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }
}
