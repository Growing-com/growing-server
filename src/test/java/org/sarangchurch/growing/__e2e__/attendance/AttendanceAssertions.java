package org.sarangchurch.growing.__e2e__.attendance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceAssertions {
    public static void 순모임_권한이_없다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getString("message")).isEqualTo("순모임 권한이 없습니다.");
    }

    public static void 순모임에_소속되지_않은_순원이_있다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getString("message")).isEqualTo("순모임에 소속되지 않은 순원이 있습니다.");
    }

    public static void 허용되지_않은_주차다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getString("message")).isEqualTo("2023년 9월부터 2024년 3월까지 출석 데이터를 입력할 수 있습니다.");
    }
}
