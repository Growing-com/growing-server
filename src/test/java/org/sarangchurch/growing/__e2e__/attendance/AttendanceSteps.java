package org.sarangchurch.growing.__e2e__.attendance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.sarangchurch.growing.__e2e__.CommonSteps;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceSteps {
    public static ExtractableResponse<Response> 민섭이가_출석을_등록한다(LocalDate week, Long teamId, Long teamMemberId) {
        Map<String, Object> attendanceParams = new HashMap<>();
        attendanceParams.put("teamMemberId", teamMemberId);
        attendanceParams.put("teamId", teamId);
        attendanceParams.put("status", "ATTEND");

        Map<String, Object> params = new HashMap<>();
        params.put("week", week);
        params.put("attendances", List.of(attendanceParams));

        return CommonSteps.givenManager()
                .body(params)
                .post("/api/attendance")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 교역자가_출석을_등록한다(LocalDate week, Long teamId, Long teamMemberId) {
        Map<String, Object> attendanceParams = new HashMap<>();
        attendanceParams.put("teamMemberId", teamMemberId);
        attendanceParams.put("teamId", teamId);
        attendanceParams.put("status", "ATTEND");

        Map<String, Object> params = new HashMap<>();
        params.put("week", week);
        params.put("attendances", List.of(attendanceParams));

        return CommonSteps.givenAdmin()
                .body(params)
                .post("/api/attendance")
                .then().log().all()
                .extract();
    }
}
