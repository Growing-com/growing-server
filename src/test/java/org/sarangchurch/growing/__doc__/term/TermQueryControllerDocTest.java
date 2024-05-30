package org.sarangchurch.growing.__doc__.term;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.term.domain.team.TeamType;
import org.sarangchurch.growing.term.query.*;
import org.sarangchurch.growing.term.query.TermQueryController;
import org.sarangchurch.growing.term.query.TermQueryRepository;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

public class TermQueryControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private TermQueryRepository termQueryRepository;

    @InjectMocks
    private TermQueryController termQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(termQueryController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("[docs] GET /api/term/{termId}/cody")
    @Test
    void name() throws Exception {
        BDDMockito.given(termQueryRepository.findCodiesByTerm(any()))
                .willReturn(List.of(new Cody(1L, "이지우")));

        ResultActions actions = mockMvc.perform(
                get("/api/term/{termId}/cody", 1L)
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-term-termId-cody",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].userId").description("코디 id"),
                                fieldWithPath("content[0].name").description("코디 이름")
                        )
                ));
    }

    @DisplayName("[docs] GET /api/term/{termId}/newTeamLeaders")
    @Test
    void name2() throws Exception {
        BDDMockito.given(termQueryRepository.findNewTeamLeadersByTerm(any()))
                .willReturn(List.of(new NewTeam(1L, "이순경")));

        ResultActions actions = mockMvc.perform(
                get("/api/term/{termId}/newTeamLeaders", 1L)
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-term-termId-newFamilyTeamLeaders",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].teamId").description("새가족반 id"),
                                fieldWithPath("content[0].leaderName").description("새가족반 순장 이름")
                        )
                ));
    }

    @DisplayName("[docs] GET /api/term/{termId}/cody/{codyId}/leaders")
    @Test
    void name3() throws Exception {
        BDDMockito.given(termQueryRepository.findLeadersOfCodyByTerm(any(), any()))
                .willReturn(List.of(new Leaders(1L, TeamType.PLANT, "우상욱")));

        ResultActions actions = mockMvc.perform(
                get("/api/term/{termId}/cody/{codyId}/leaders", 1L, 2L)
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-term-termId-cody-codyId-leaders",
                        pathParameters(
                                parameterWithName("termId").description("텀 id"),
                                parameterWithName("codyId").description("코디 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].teamId").description("순모임 id"),
                                fieldWithPath("content[0].teamType").description("순모임 종류"),
                                fieldWithPath("content[0].leaderName").description("순장 이름")
                        )
                ));
    }

    @DisplayName("[docs] GET /api/term")
    @Test
    void name4() throws Exception {
        BDDMockito.given(termQueryRepository.findAll())
                .willReturn(List.of(new Term(1L, LocalDate.of(2023, 9, 1), LocalDate.of(2024, 3, 1))));

        ResultActions actions = mockMvc.perform(
                get("/api/term", 1L, 2L)
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-term",
                        responseFields(
                                fieldWithPath("content[0].id").description("텀 id"),
                                fieldWithPath("content[0].startDate").description("텀 시작일"),
                                fieldWithPath("content[0].endDate").description("텀 종료일")
                        )
                ));
    }

    @DisplayName("[docs] GET /api/term/totalUser")
    @Test
    void name5() throws Exception {
        BDDMockito.given(termQueryRepository.findTotalUser(any()))
                .willReturn(new TotalUser(450L, 5L));

        ResultActions actions = mockMvc.perform(
                get("/api/term/totalUser", 1L, 2L)
                        .param("week", "2023-10-01")
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-term-totalUser",
                        requestParameters(
                                parameterWithName("week").description("주차")
                        ),
                        responseFields(
                                fieldWithPath("content.totalRegistered").description("재적인원 수"),
                                fieldWithPath("content.totalNewRegistered").description("등록인원 수")
                        )
                ));
    }

    @DisplayName("[docs] GET /api/term/{termId}/newComers")
    @Test
    void name6() throws Exception {
        BDDMockito.given(termQueryRepository.findNewComersByTerm(any()))
                .willReturn(List.of(new NewComer(
                        1L,
                        1L,
                        1L,
                        "강라임",
                        9,
                        Sex.FEMALE,
                        Duty.NEW_COMER,
                        Role.NORMAL,
                        "010-1234-1234",
                        LocalDate.of(1996, 5, 1),
                        LocalDate.of(2023, 10, 1),
                        null,
                        null,
                        "조예원",
                        null,
                        null,
                        true,
                        "비고",
                        LocalDateTime.now(),
                        "이순종"
                )));

        ResultActions actions = mockMvc.perform(
                get("/api/term/{termId}/newComers", 1L)
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-term-termId-newComers",
                        pathParameters(
                                parameterWithName("termId").description("텀 id")
                        ),
                        responseFields(
                                fieldWithPath("content[0].userId").description("유저 id"),
                                fieldWithPath("content[0].teamId").description("새가족반 id"),
                                fieldWithPath("content[0].teamMemberId").description("순원 id"),
                                fieldWithPath("content[0].name").description("유저 이름"),
                                fieldWithPath("content[0].grade").description("유저 학년"),
                                fieldWithPath("content[0].sex").description("유저 성별"),
                                fieldWithPath("content[0].duty").description("유저 직분"),
                                fieldWithPath("content[0].role").description("유저 역할"),
                                fieldWithPath("content[0].phoneNumber").description("유저 번호"),
                                fieldWithPath("content[0].birth").description("유저 생일"),
                                fieldWithPath("content[0].visitDate").description("유저 방문일"),
                                fieldWithPath("content[0].lineupDate").type(JsonFieldType.STRING).description("등반 날짜").optional(),
                                fieldWithPath("content[0].lineoutDate").type(JsonFieldType.STRING).description("라인아웃 날짜").optional(),
                                fieldWithPath("content[0].newTeamLeaderName").description("새가족반 순장 이름"),
                                fieldWithPath("content[0].firstPlantManagerName").type(JsonFieldType.STRING).description("첫 순모임 코디 이름").optional(),
                                fieldWithPath("content[0].firstPlantLeaderName").type(JsonFieldType.STRING).description("첫 순모임 순장 이름").optional(),
                                fieldWithPath("content[0].isActive").description("재적 여부"),
                                fieldWithPath("content[0].etc").description("비고").optional(),
                                fieldWithPath("content[0].updatedAt").description("수정 시간").optional(),
                                fieldWithPath("content[0].updatedBy").description("수정자").optional()
                        )
                ));
    }
}
