package org.sarangchurch.growing.__e2e__.term;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.sarangchurch.growing.__e2e__.CommonSteps;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TermSteps {
    public static ExtractableResponse<Response> 목사님이_등반을_요청한다(long teamId, long teamMemberId) {
        Map<String, Object> params = new HashMap<>();
        long 문은선_순모임 = 20L;

        params.put("plantTeamId", 문은선_순모임);
        params.put("lineupDate", LocalDate.of(2023, 10, 1));
        params.put("gradeAtFirstVisit", 9);

        return CommonSteps.givenAdmin()
                .body(params)
                .post("/api/team/{teamId}/teamMember/{teamMemberId}/lineup", teamId, teamMemberId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 목사님이_라인아웃을_요청한다(long teamId, long teamMemberId) {
        Map<String, Object> params = new HashMap<>();

        params.put("lineoutDate", LocalDate.of(2023, 10, 1));
        params.put("gradeAtFirstVisit", 9);

        return CommonSteps.givenAdmin()
                .body(params)
                .post("/api/team/{teamId}/teamMember/{teamMemberId}/lineout", teamId, teamMemberId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 목사님이_새가족을_등록한다(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("sex", "MALE");
        params.put("phoneNumber", "010-1234-1234");
        params.put("grade", "1");
        params.put("teamId", 38);
        params.put("visitTermId", 1);
        params.put("visitDate", LocalDate.of(2023, 10, 1));

        return CommonSteps.givenAdmin()
                .body(params)
                .post("/api/user")
                .then().log().all()
                .extract();
    }
}
