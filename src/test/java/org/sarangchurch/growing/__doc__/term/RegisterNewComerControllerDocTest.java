package org.sarangchurch.growing.__doc__.term;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.term.application.dto.RegisterNewComerRequest;
import org.sarangchurch.growing.term.application.facade.RegisterNewComerFacade;
import org.sarangchurch.growing.term.presentation.RegisterNewComerController;
import org.sarangchurch.growing.user.domain.Sex;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterNewComerControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private RegisterNewComerFacade registerNewComerFacade;

    @InjectMocks
    private RegisterNewComerController registerNewComerController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(registerNewComerController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("[docs] POST /api/user")
    @Test
    void name() throws Exception {
        RegisterNewComerRequest request = RegisterNewComerRequest.builder()
                .name("우상욱")
                .sex(Sex.MALE)
                .phoneNumber("010-1234-1234")
                .birth(LocalDate.of(1996, 10, 16))
                .grade(9)
                .teamId(1L)
                .visitTermId(1L)
                .visitDate(LocalDate.of(2023, 10, 1))
                .etc("비교")
                .build();

        BDDMockito.doNothing()
                .when(registerNewComerFacade)
                .register(any());

        ResultActions actions = mockMvc.perform(
                post("/api/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-api-user",
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("sex").description("성별"),
                                fieldWithPath("phoneNumber").description("번호").optional(),
                                fieldWithPath("birth").description("생일").optional(),
                                fieldWithPath("grade").description("학년"),
                                fieldWithPath("teamId").description("새가족반 id"),
                                fieldWithPath("visitTermId").description("방문한 텀 id"),
                                fieldWithPath("visitDate").description("방문 날짜"),
                                fieldWithPath("etc").description("비고").optional()
                        )
                ));
    }
}
