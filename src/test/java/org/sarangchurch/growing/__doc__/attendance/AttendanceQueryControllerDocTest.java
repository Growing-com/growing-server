package org.sarangchurch.growing.__doc__.attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.__doc__.DocUtils;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.attendance.query.attendance.AttendanceQueryController;
import org.sarangchurch.growing.attendance.query.attendance.AttendanceSearchResult;
import org.sarangchurch.growing.attendance.query.attendance.CodyWeeklyAttendanceResult;
import org.sarangchurch.growing.attendance.query.attendance.AttendanceQueryRepository;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.data.domain.PageImpl;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttendanceQueryControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private AttendanceQueryRepository attendanceQueryRepository;

    @InjectMocks
    private AttendanceQueryController attendanceQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceQueryController)
                .apply(documentationConfiguration(restDocumentation))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .build();
    }

    @DisplayName("[docs] GET /api/attendance/search")
    @Test
    void name() throws Exception {
        // given
        AttendanceSearchResult.AttendanceItem attendanceItem = AttendanceSearchResult.AttendanceItem
                .builder()
                .attendanceId(1L)
                .status(AttendanceStatus.ATTEND)
                .week(Week.previousSunday(LocalDate.now()))
                .etc("비고")
                .build();

        AttendanceSearchResult result = AttendanceSearchResult.builder()
                .leaderName("우상욱")
                .managerName("이지우")
                .userId(1L)
                .userName("김세라")
                .grade(6)
                .sex(Sex.FEMALE)
                .build();

        result.getAttendanceItems().add(attendanceItem);

        BDDMockito.given(attendanceQueryRepository.findByUserNamePrefixOrUserAge(any(), any()))
                .willReturn(new PageImpl<>(List.of(result)));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/attendance/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("startDate", "2023-10-01")
                        .param("endDate", "2023-11-01")
                        .param("name", "우")
                        .param("grade", "9")
                        .param("codyId", "1,2")
                        .param("isNewOnly", "true")
                        .param("page", "0")
                        .param("size", "10")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-attendance-search",
                        requestParameters(
                                parameterWithName("startDate").description("시작 날짜"),
                                parameterWithName("endDate").description("마지막 날짜"),
                                parameterWithName("name").description("이름(prefix)").optional(),
                                parameterWithName("grade").description("학년").optional(),
                                parameterWithName("codyId").description("코디 id").optional(),
                                parameterWithName("isNewOnly").description("새가족만 검색 여부").optional(),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 개수")
                        ),
                        responseFields(DocUtils.getPagingFieldDescriptors(
                                        fieldWithPath("content[0].leaderName").description("순장 이름"),
                                        fieldWithPath("content[0].managerName").description("코디 이름"),
                                        fieldWithPath("content[0].userId").description("순원 id"),
                                        fieldWithPath("content[0].userName").description("순원 이름"),
                                        fieldWithPath("content[0].grade").description("순원 학년"),
                                        fieldWithPath("content[0].sex").description("순원 성별"),
                                        fieldWithPath("content[0].attendanceItems[0].attendanceId").description("출석 id"),
                                        fieldWithPath("content[0].attendanceItems[0].status").description("출석"),
                                        fieldWithPath("content[0].attendanceItems[0].week").description("출석 주차"),
                                        fieldWithPath("content[0].attendanceItems[0].etc").description("출석 비고").optional()
                                )
                        )));
    }

    @DisplayName("[docs] GET /api/attendance")
    @Test
    void name2() throws Exception {
        // given
        CodyWeeklyAttendanceResult codyWeeklyAttendanceResult = new CodyWeeklyAttendanceResult(
                "우상욱",
                1L,
                1L,
                "김세라",
                6,
                Sex.FEMALE,
                1L,
                AttendanceStatus.ATTEND,
                Week.previousSunday(LocalDate.now()),
                "비고"
        );

        BDDMockito.given(attendanceQueryRepository.findByCodyAndWeek(any(), any()))
                .willReturn(List.of(codyWeeklyAttendanceResult));

        // when
        ResultActions actions = mockMvc.perform(
                get("/api/attendance")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("codyId", "1")
                        .param("week", "2023-11-01")
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-attendance",
                        requestParameters(
                                parameterWithName("codyId").description("코디 id"),
                                parameterWithName("week").description("주차")
                        ),
                        responseFields(
                                fieldWithPath("content[0].leaderName").description("순장 이름"),
                                fieldWithPath("content[0].teamId").description("코디 이름"),
                                fieldWithPath("content[0].teamMemberId").description("순원 id"),
                                fieldWithPath("content[0].userName").description("순원 이름"),
                                fieldWithPath("content[0].grade").description("순원 학년"),
                                fieldWithPath("content[0].sex").description("순원 성별"),
                                fieldWithPath("content[0].attendanceId").description("출석 id"),
                                fieldWithPath("content[0].status").description("출석"),
                                fieldWithPath("content[0].week").description("출석 주차"),
                                fieldWithPath("content[0].etc").description("출석 비고").optional()

                        )));
    }
}
