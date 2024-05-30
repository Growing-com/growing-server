package org.sarangchurch.growing.__doc__.training;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

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

public class RegisterTrainingMemebersControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private RegisterTrainingMemberService registerTrainingMemberService;

    @InjectMocks
    private RegisterTrainingMembersController registerTrainingMembersController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(registerTrainingMembersController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("[docs] POST /api/trainings/{trainingId}/registerMembers")
    @Test
    void name() throws Exception {
        RegisterTrainingMembersRequest request = new RegisterTrainingMembersRequest(List.of(1L));

        BDDMockito.doNothing()
                .when(registerTrainingMemberService)
                .register(any(), any());

        ResultActions actions = mockMvc.perform(
                post("/api/trainings/{trainingId}/registerMembers", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-api-trainings-trainingId-registerMember",
                        pathParameters(
                                parameterWithName("trainingId").description("훈련 id")
                        ),
                        requestFields(
                                fieldWithPath("userIds").description("등록 유저 id 배열")
                        )
                ));
    }
}
