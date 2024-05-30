package org.sarangchurch.growing.__doc__.attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.attendance.query.excel.AttendanceExcelQueryController;
import org.sarangchurch.growing.attendance.query.excel.AttendanceExcelQueryRepository;
import org.sarangchurch.growing.attendance.query.excel.grade.WeeklyGradeAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.leader.WeeklyLeaderAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.manager.WeeklyManagerAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.personal.WeeklyPersonalAttendanceResult;
import org.sarangchurch.growing.attendance.query.excel.rate.TermPersonalAttendanceRateResult;
import org.sarangchurch.growing.core.config.JobRunner;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.batch.core.ExitStatus;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttendanceExcelQueryControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private AttendanceExcelQueryRepository attendanceExcelQueryRepository;

    @Mock
    private JobRunner jobRunner;

    @InjectMocks
    private AttendanceExcelQueryController attendanceExcelQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceExcelQueryController)
                .apply(documentationConfiguration(restDocumentation))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .build();
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/personalAttendance")
    @Test
    void name() throws Exception {
        // given
        WeeklyPersonalAttendanceResult.WeeklyPersonalAttendanceItem attendanceItem = new WeeklyPersonalAttendanceResult.WeeklyPersonalAttendanceItem(
                Week.previousSunday(LocalDate.of(2023, 10, 1)),
                AttendanceStatus.ATTEND
        );

        WeeklyPersonalAttendanceResult result = WeeklyPersonalAttendanceResult.builder()
                .managerId(1L)
                .managerName("이지우")
                .leaderName("우상욱")
                .userName("김세라")
                .sex(Sex.FEMALE)
                .grade(6)
                .phoneNumber("010-1234-1234")
                .build();

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceExcelQueryRepository.findAllPersonalAttendanceByTerm(any()))
                .willReturn(List.of(result));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/personalAttendance", 1L)
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-personalAttendance",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].managerId").description("코디 id"),
                                fieldWithPath("content[0].managerName").description("코디 이름"),
                                fieldWithPath("content[0].leaderName").description("순장 이름"),
                                fieldWithPath("content[0].userName").description("순원 이름"),
                                fieldWithPath("content[0].sex").description("순원 성별"),
                                fieldWithPath("content[0].grade").description("순원 학년"),
                                fieldWithPath("content[0].phoneNumber").description("순원 번호"),
                                fieldWithPath("content[0].attendanceItems[0].status").description("출석"),
                                fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차")
                        )));
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/newFamilyAttendance")
    @Test
    void name2() throws Exception {
        // given
        WeeklyPersonalAttendanceResult.WeeklyPersonalAttendanceItem attendanceItem = new WeeklyPersonalAttendanceResult.WeeklyPersonalAttendanceItem(
                Week.previousSunday(LocalDate.of(2023, 10, 1)),
                AttendanceStatus.ATTEND
        );

        WeeklyPersonalAttendanceResult result = WeeklyPersonalAttendanceResult.builder()
                .managerId(1L)
                .managerName("이지우")
                .leaderName("우상욱")
                .userName("김세라")
                .sex(Sex.FEMALE)
                .grade(6)
                .phoneNumber("010-1234-1234")
                .build();

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceExcelQueryRepository.findAllNewFamilyAttendanceByTerm(any()))
                .willReturn(List.of(result));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/newFamilyAttendance", 1L)
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-newFamilyAttendance",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].managerId").description("코디 id"),
                                fieldWithPath("content[0].managerName").description("코디 이름"),
                                fieldWithPath("content[0].leaderName").description("순장 이름"),
                                fieldWithPath("content[0].userName").description("순원 이름"),
                                fieldWithPath("content[0].sex").description("순원 성별"),
                                fieldWithPath("content[0].grade").description("순원 학년"),
                                fieldWithPath("content[0].phoneNumber").description("순원 번호"),
                                fieldWithPath("content[0].attendanceItems[0].status").description("출석"),
                                fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차")
                        )));
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/leaderAttendance")
    @Test
    void name3() throws Exception {
        // given
        WeeklyLeaderAttendanceResult.WeeklyLeaderAttendanceItem attendanceItem = WeeklyLeaderAttendanceResult.WeeklyLeaderAttendanceItem
                .builder()
                .week(Week.previousSunday(LocalDate.of(2023, 10, 1)))
                .totalRegistered(12L)
                .totalAttendance(5L)
                .build();

        WeeklyLeaderAttendanceResult result = WeeklyLeaderAttendanceResult.builder()
                .managerId(1L)
                .managerName("이지우")
                .leaderId(2L)
                .leaderName("우상욱")
                .sex(Sex.MALE)
                .grade(9)
                .phoneNumber("010-1234-1234")
                .build();

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceExcelQueryRepository.findAllLeaderAttendanceByTerm(any()))
                .willReturn(List.of(result));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/leaderAttendance", 1L)
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-leaderAttendance",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].managerId").description("코디 id"),
                                fieldWithPath("content[0].managerName").description("코디 이름"),
                                fieldWithPath("content[0].leaderId").description("순장 id"),
                                fieldWithPath("content[0].leaderName").description("순장 이름"),
                                fieldWithPath("content[0].sex").description("순원 성별"),
                                fieldWithPath("content[0].grade").description("순원 학년"),
                                fieldWithPath("content[0].phoneNumber").description("순원 번호"),
                                fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차"),
                                fieldWithPath("content[0].attendanceItems[0].totalRegistered").description("순모임 재적 인원"),
                                fieldWithPath("content[0].attendanceItems[0].totalAttendance").description("순모임 출석 인원")
                        )));
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/managerAttendance")
    @Test
    void name4() throws Exception {
        // given
        WeeklyManagerAttendanceResult.WeeklyManagerAttendanceItem attendanceItem = WeeklyManagerAttendanceResult.WeeklyManagerAttendanceItem
                .builder()
                .week(Week.previousSunday(LocalDate.of(2023, 10, 1)))
                .totalRegistered(52L)
                .totalAttendance(25L)
                .build();

        WeeklyManagerAttendanceResult result = new WeeklyManagerAttendanceResult(
                1L,
                "이지우"
        );

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceExcelQueryRepository.findAllManagerAttendanceByTerm(any()))
                .willReturn(List.of(result));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/managerAttendance", 1L)
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-managerAttendance",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].managerId").description("코디 id"),
                                fieldWithPath("content[0].managerName").description("코디 이름"),
                                fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차"),
                                fieldWithPath("content[0].attendanceItems[0].totalRegistered").description("나무 재적 인원"),
                                fieldWithPath("content[0].attendanceItems[0].totalAttendance").description("나무 출석 인원")
                        )));
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/gradeAttendance")
    @Test
    void name5() throws Exception {
        // given
        WeeklyGradeAttendanceResult.WeeklyGradeAttendanceItem attendanceItem = WeeklyGradeAttendanceResult.WeeklyGradeAttendanceItem
                .builder()
                .week(Week.previousSunday(LocalDate.of(2023, 10, 1)))
                .totalRegistered(52L)
                .totalAttendance(25L)
                .build();

        WeeklyGradeAttendanceResult result = new WeeklyGradeAttendanceResult(
                9
        );

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceExcelQueryRepository.findAllGradeAttendanceByTerm(any()))
                .willReturn(List.of(result));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/gradeAttendance", 1L)
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-gradeAttendance",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].grade").description("학년"),
                                fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차"),
                                fieldWithPath("content[0].attendanceItems[0].totalRegistered").description("나무 재적 인원"),
                                fieldWithPath("content[0].attendanceItems[0].totalAttendance").description("나무 출석 인원")
                        )));
    }

    @DisplayName("[docs] GET /api/statistics/term/{termId}/attendanceRate")
    @Test
    void name6() throws Exception {
        // given
        TermPersonalAttendanceRateResult.TermPersonalAttendanceRateItem attendanceItem = new TermPersonalAttendanceRateResult.TermPersonalAttendanceRateItem(
                "우상욱",
                Sex.MALE,
                9,
                "010-1234-1234",
                20L,
                19L,
                0L,
                1L
        );

        TermPersonalAttendanceRateResult result = new TermPersonalAttendanceRateResult(
                "2023-2학기",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2024, 3, 1),
                List.of(attendanceItem)
        );

        BDDMockito.given(jobRunner.run(any(), any()))
                .willReturn(ExitStatus.COMPLETED);

        BDDMockito.given(attendanceExcelQueryRepository.findAllAttendanceRateByTerm(any()))
                .willReturn(result);

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/statistics/term/{termId}/attendanceRate", 1L)
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-statistics-term-termId-attendanceRate",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content.name").description("텀 이름"),
                                fieldWithPath("content.startDate").description("텀 시작 날짜"),
                                fieldWithPath("content.endDate").description("텀 종료 날짜"),
                                fieldWithPath("content.attendanceRateItems[0].userName").description("이름"),
                                fieldWithPath("content.attendanceRateItems[0].sex").description("성별"),
                                fieldWithPath("content.attendanceRateItems[0].grade").description("학년"),
                                fieldWithPath("content.attendanceRateItems[0].phoneNumber").description("번호"),
                                fieldWithPath("content.attendanceRateItems[0].totalWeekPassed").description("총 재적 기간"),
                                fieldWithPath("content.attendanceRateItems[0].totalAttend").description("총 출석"),
                                fieldWithPath("content.attendanceRateItems[0].totalOnline").description("총 온라인"),
                                fieldWithPath("content.attendanceRateItems[0].totalAbsent").description("총 결석")
                        )));
    }
}
