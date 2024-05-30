package org.sarangchurch.growing.__e2e__.user;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.sarangchurch.growing.__e2e__.CommonSteps;

import java.util.HashMap;
import java.util.Map;

public class UserSteps {
    public static ExtractableResponse<Response> 교역자가_유저를_전체_조회한다() {
        return CommonSteps.givenAdmin()
                .get("/api/user")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 민섭이가_유저를_전체_조회한다() {
        return CommonSteps.givenManager()
                .get("/api/user")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상욱이가_유저를_전체_조회한다() {
        return CommonSteps.givenNormal()
                .get("/api/user")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상욱이가_자기_정보를_조회한다() {
        return CommonSteps.givenNormal()
                .get("/api/user/myInfo")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 교역자가_유저의_이름을_수정한다(Long userId, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("sex", "FEMALE");
        params.put("phoneNumber", "010-1234-1234");
        params.put("grade", 1);
        params.put("isActive", true);

        return CommonSteps.givenAdmin()
                .body(params)
                .put("/api/user/{userId}", userId)
                .then().log().all()
                .extract();

    }
}
