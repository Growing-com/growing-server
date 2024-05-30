package org.sarangchurch.growing.__doc__.training;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.training.application.training.dto.CreateTrainingRequest;
import org.sarangchurch.growing.training.application.training.service.CreateTrainingService;
import org.sarangchurch.growing.training.domain.training.TrainingType;
import org.sarangchurch.growing.training.presentation.training.CreateTrainingController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateTrainingControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private CreateTrainingService createTrainingService;

    @InjectMocks
    private CreateTrainingController createTrainingController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(createTrainingController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("[docs] POST /api/user")
    @Test
    void name() throws Exception {
        CreateTrainingRequest request = CreateTrainingRequest.builder()
                .type(TrainingType.DISCIPLE_SCHOOL_A)
                .name("2023-2 제자학교A")
                .startDate(LocalDate.of(2023, 9, 1))
                .endDate(LocalDate.of(2023, 11, 1))
                .etc("비고")
                .build();

        BDDMockito.doNothing()
                .when(createTrainingService)
                .create(any());

        ResultActions actions = mockMvc.perform(
                post("/api/training")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-api-training",
                        requestFields(
                                fieldWithPath("type").description("종류 (trainingType)"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("startDate").description("시작일"),
                                fieldWithPath("endDate").description("종료일").optional(),
                                fieldWithPath("etc").description("비고").optional()
                        )
                ));
    }
}
