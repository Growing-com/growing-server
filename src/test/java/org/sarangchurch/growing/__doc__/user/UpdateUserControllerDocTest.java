package org.sarangchurch.growing.__doc__.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.user.application.dto.UpdateUserRequest;
import org.sarangchurch.growing.user.application.service.UpdateUserService;
import org.sarangchurch.growing.user.domain.Sex;
import org.sarangchurch.growing.user.presentation.UpdateUserController;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateUserControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private UpdateUserService updateUserService;

    @InjectMocks
    private UpdateUserController updateUserController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(updateUserController)
                .apply(documentationConfiguration(restDocumentation))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .build();
    }

    @DisplayName("[docs] PUT /api/user/{userId}")
    @Test
    void name() throws Exception {
        // given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("우상욱")
                .sex(Sex.MALE)
                .phoneNumber("010-1234-1234")
                .birth(LocalDate.of(1996, 10, 16))
                .grade(9)
                .isActive(true)
                .visitDate(LocalDate.of(2016, 12, 1))
                .etc("비고")
                .build();

        BDDMockito.doNothing()
                .when(updateUserService)
                .update(any(), any());

        // when
        ResultActions actions = mockMvc.perform(put("/api/user/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("put-api-user-userId",
                        pathParameters(
                                parameterWithName("userId").description("유저 id")
                        ),
                        requestFields(
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("sex").description("유저 성별"),
                                fieldWithPath("phoneNumber").description("유저 전화번호"),
                                fieldWithPath("birth").description("유저 생년월일").optional(),
                                fieldWithPath("grade").description("유저 학년"),
                                fieldWithPath("isActive").description("재적 여부"),
                                fieldWithPath("visitDate").description("방문 날짜").optional(),
                                fieldWithPath("etc").description("비고").optional()
                        )));
    }
}
