package org.sarangchurch.growing.__e2e__.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthSteps {
    public static ExtractableResponse<Response> 로그인_여부를_확인한다() {
        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .when()
                .get("/api/auth/isLoggedIn")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 로그인한다(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .extract();
    }
}
