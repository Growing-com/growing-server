package org.sarangchurch.growing.__doc__.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.v1.feat.auth.presentation.AuthController;
import org.sarangchurch.growing.core.types.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private AuthController authController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("[docs] POST /api/auth/logout")
    @Test
    void logout() throws Exception {
        BDDMockito.doNothing()
                .when(authController)
                .logout(any(), any());

        ResultActions actions = mockMvc.perform(
                post("/api/auth/logout")
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-api-auth-logout"));
    }

    @DisplayName("[docs] GET /api/auth/isLoggedIn")
    @Test
    void isLoggedIn() throws Exception {
        BDDMockito.given(authController.isLoggedIn(any()))
                .willReturn(ApiResponse.of(false));


        ResultActions actions = mockMvc.perform(
                get("/api/auth/isLoggedIn")
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-auth-isLoggedIn",
                        responseFields(
                                fieldWithPath("content").description("로그인 여부")
                        )
                ));
    }
}
