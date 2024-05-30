package org.sarangchurch.growing.__e2e__.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthAssertions {
    public static void 로그인이_안되어있다(ExtractableResponse<Response> response) {
        assertThat(response.jsonPath().getBoolean("content")).isFalse();
    }
}
