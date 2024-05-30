package org.sarangchurch.growing.__doc__.attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.attendance.query.stats.*;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttendanceStatsQueryControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private AttendanceStatsQueryRepository attendanceStatsQueryRepository;

    @InjectMocks
    private AttendanceStatsQueryController attendanceStatsQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceStatsQueryController)
                .apply(documentationConfiguration(restDocumentation))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .build();
    }

    @DisplayName("[docs] GET /api/statistics/attendance")
    @Test
    void name() throws Exception {
        // given
        WeeklyPersonalAttendanceSummaryResult.AttendanceItem attendanceItem = WeeklyPersonalAttendanceSummaryResult.AttendanceItem
                .builder()
                .attendanceId(1L)
                .status(AttendanceStatus.ABSENT)
                .week(Week.previousSunday(LocalDate.now()))
                .etc("비고")
                .build();

        WeeklyPersonalAttendanceSummaryResult result = WeeklyPersonalAttendanceSummaryResult.builder()
                .leaderName("우상욱")
                .managerName("이지우")
                .userId(1L)
                .userName("김세라")
                .grade(6)
                .sex(Sex.FEMALE)
                .build();

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceStatsQueryRepository.findAbsentByDateBetween(any(), any()))
                .willReturn(List.of(result));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/attendance")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("startDate", "2023-10-01")
                        .param("endDate", "2023-11-01")
                        .param("isAbsent", "true")
                        .param("isNewOnly", "false")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-attendance",
                        requestParameters(
                                parameterWithName("startDate").description("시작 날짜"),
                                parameterWithName("endDate").description("마지막 날짜"),
                                parameterWithName("isAbsent").description("결석 여부").optional(),
                                parameterWithName("isNewOnly").description("(현)새가족 여부").optional()
                        ),
                        responseFields(
                                fieldWithPath("content[0].leaderName").description("순장 이름"),
                                fieldWithPath("content[0].managerName").description("코디 이름"),
                                fieldWithPath("content[0].userId").description("순원 id"),
                                fieldWithPath("content[0].userName").description("순원 이름"),
                                fieldWithPath("content[0].userPhone").description("순원 번호"),
                                fieldWithPath("content[0].grade").description("순원 학년"),
                                fieldWithPath("content[0].sex").description("순원 성별"),
                                fieldWithPath("content[0].attendanceItems[0].attendanceId").description("출석 id"),
                                fieldWithPath("content[0].attendanceItems[0].status").description("출석"),
                                fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차"),
                                fieldWithPath("content[0].attendanceItems[0].etc").description("출석 비고").optional()
                        )
                ));
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/attendanceProgress")
    @Test
    void name2() throws Exception {
        // given
        AttendanceProgressResult.NotProgressedLeaders notProgressedLeaders = new AttendanceProgressResult.NotProgressedLeaders(
                1L,
                "곽민섭"
        );

        AttendanceProgressResult result = new AttendanceProgressResult(
                Week.previousSunday(LocalDate.of(2023, 10, 1)),
                453L,
                400L,
                List.of(notProgressedLeaders)
        );

        BDDMockito.given(attendanceStatsQueryRepository.getAttendanceProgressByTermAndWeek(any(), any()))
                .willReturn(result);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/attendanceProgress", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("week", "2023-10-01")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-attendanceProgress",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        requestParameters(
                                parameterWithName("week").description("주차")
                        ),
                        responseFields(
                                fieldWithPath("content.week").description("주차"),
                                fieldWithPath("content.totalRegistered").description("총 재적인원"),
                                fieldWithPath("content.totalProgressed").description("출석 처리 완료 인원"),
                                fieldWithPath("content.notProgressedLeaders[0].userId").description("출석 처리 0개 순장 id"),
                                fieldWithPath("content.notProgressedLeaders[0].name").description("출석 처리 0개 순장 이름")
                        )
                ));
    }

    @DisplayName("[docs] GET /api/statistics/attendanceSummary")
    @Test
    void name3() throws Exception {
        // given
        WeeklyAttendanceSummaryResult weeklyAttendanceSummaryResult = new WeeklyAttendanceSummaryResult(
                Week.previousSunday(LocalDate.of(2023, 10, 1)),
                453L,
                200L,
                0L,
                253L,
                253L,
                100L,
                200L,
                100L,
10L,
                5L,
                3L
        );

        BDDMockito.given(attendanceStatsQueryRepository.findSummaryByWeek(any(), any()))
                .willReturn(List.of(weeklyAttendanceSummaryResult));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/attendanceSummary", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("startDate", "2023-10-01")
                        .param("endDate", "2023-11-01")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-attendanceSummary",
                        requestParameters(
                                parameterWithName("startDate").description("시작 주차(inclusvie)"),
                                parameterWithName("endDate").description("끝 주차(inclusvie)")
                        ),
                        responseFields(
                                fieldWithPath("content[].week").description("주차"),
                                fieldWithPath("content[].totalRegistered").description("총 재적인원"),
                                fieldWithPath("content[].totalAttendance").description("총 출석 인원"),
                                fieldWithPath("content[].totalOnline").description("총 온라인 인원"),
                                fieldWithPath("content[].totalAbsent").description("총 결석 인원"),
                                fieldWithPath("content[].maleRegistered").description("남자 재적인원"),
                                fieldWithPath("content[].maleAttendance").description("남자 출석 인원(온라인 포함)"),
                                fieldWithPath("content[].femaleRegistered").description("여자 재적인원"),
                                fieldWithPath("content[].femaleAttendance").description("여자 출석 인원(온라인 포함)"),
                                fieldWithPath("content[].newComerRegistered").description("새가족 재적 인원(등반 제외)"),
                                fieldWithPath("content[].newComerAttendance").description("새가족 출석 인원(온라인 포함, 등반 제외)"),
                                fieldWithPath("content[].newVisited").description("새등록 인원")
                        )
                ));
    }
}
