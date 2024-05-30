package org.sarangchurch.growing.__e2e__;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CommonSteps {

    public static RequestSpecification givenAdmin() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "soonjong8");
        params.put("password", "leader");

        Cookies cookies = RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .extract()
                .response()
                .getDetailedCookies();

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .cookies(cookies);
    }

    public static RequestSpecification givenManager() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "minseop8");
        params.put("password", "leader");

        Cookies cookies = RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .extract()
                .response()
                .getDetailedCookies();

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .cookies(cookies);
    }

    public static RequestSpecification givenNormal() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "sangwook8");
        params.put("password", "leader");

        Cookies cookies = RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .extract()
                .response()
                .getDetailedCookies();

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .cookies(cookies);
    }
}
