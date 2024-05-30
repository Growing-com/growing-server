package org.sarangchurch.growing.__doc__.term;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.term.application.dto.LineupNewComerRequest;
import org.sarangchurch.growing.term.application.facade.LineupNewComerFacade;
import org.sarangchurch.growing.term.presentation.LineupNewComerController;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LineupNewComerControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private LineupNewComerFacade lineupNewComerFacade;

    @InjectMocks
    private LineupNewComerController lineupNewComerController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(lineupNewComerController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("[docs] POST /api/team/{teamId}/teamMember/{teamMemberId}/lineup")
    @Test
    void name() throws Exception {
        LineupNewComerRequest request = new LineupNewComerRequest(
                1L,
                LocalDate.of(2023, 10, 1),
                9
        );

        BDDMockito.doNothing()
                .when(lineupNewComerFacade)
                .lineup(any(), any(), any());

        ResultActions actions = mockMvc.perform(
                post("/api/team/{teamId}/teamMember/{teamMemberId}/lineup", 1L, 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-api-team-teamId-teamMember-teamMemberId-lineup",
                        pathParameters(
                          parameterWithName("teamId").description("새가족반 id"),
                          parameterWithName("teamMemberId").description("새가족 순원 id")
                        ),
                        requestFields(
                                fieldWithPath("plantTeamId").description("등반 순모임 id"),
                                fieldWithPath("lineupDate").description("등반 날짜"),
                                fieldWithPath("gradeAtFirstVisit").description("방문 당시 학년")
                        )
                ));
    }
}
