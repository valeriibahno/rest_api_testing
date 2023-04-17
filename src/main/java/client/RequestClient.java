package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.book.Book;
import utils.EnvConfig;
import utils.PropertyManager;

import static io.restassured.RestAssured.given;

public class RequestClient {

    private static final PropertyManager env = EnvConfig.getEnvInstance();

    public static Response get(String endPoint) {
        return RequestClient.sendRequest(Method.GET, endPoint);
    }

    public static Response post(String endPoint, Book body) {
        return RequestClient.sendRequest(Method.POST, endPoint, body);
    }

    public static Response put(String endPoint, Book body) {
        return RequestClient.sendRequest(Method.PUT, endPoint, body);
    }

    public static Response delete(String endPoint) {
        return RequestClient.sendRequest(Method.DELETE, endPoint);
    }

    private static Response sendRequest(Method method, String endPoint) {
        return RequestClient.sendRequest(method, endPoint, null);
    }

    private static Response sendRequest(Method method, String endPoint, Book body) {
        String url = env.getPropertyValue("service.host") + endPoint;
        RequestSpecification spec = given().auth().preemptive().basic("admin", "password").filter(new AllureRestAssured());
        if(body != null) {
            spec.contentType("application/json").body(body);
        }
        Response response = spec.request(method, url);
        response.then().log().all();
        return response;
    }
}
