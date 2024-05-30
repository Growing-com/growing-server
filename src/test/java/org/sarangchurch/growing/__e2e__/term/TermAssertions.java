package org.sarangchurch.growing.__e2e__.term;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class TermAssertions {
    public static void 중복_이름으로_새가족을_등록할_수_없다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getString("message")).isEqualTo("이미 존재하는 이름입니다.");
    }
}
