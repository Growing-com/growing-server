package org.sarangchurch.growing.__e2e__.user;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAssertions {
    public static void 유저수가_일치한다(ExtractableResponse<Response> response, int expected) {
        assertThat(response.body().jsonPath().getList("content").size()).isEqualTo(expected);
    }

    public static void 내_정보가_존재한다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getLong("content.id")).isNotNull();
    }

    public static void 유저_수정에_실패한다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getString("message")).isEqualTo("이미 존재하는 이름입니다.");
    }
}
