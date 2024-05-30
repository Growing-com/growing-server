package org.sarangchurch.growing.__doc__.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;
import org.sarangchurch.growing.user.query.User;
import org.sarangchurch.growing.user.query.UserQueryController;
import org.sarangchurch.growing.user.query.UserQueryRepository;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserQueryControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private UserQueryController userQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(userQueryController)
                .apply(documentationConfiguration(restDocumentation))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .build();
    }

    @DisplayName("[docs] GET /api/user")
    @Test
    void findAll() throws Exception {
        given(userQueryRepository.findAllAccounts()).willReturn(List.of(
                new User(1L,
                        "우상욱",
                        9,
                        Sex.MALE,
                        Duty.MEMBER,
                        Role.MANAGER,
                        "01030387440",
                        LocalDate.of(1996, 10, 16),
                        true,
                        LocalDate.of(2016, 12, 2),
                        "",
                        LocalDateTime.now(),
                        "이종민"
                )
        ));

        ResultActions actions = mockMvc.perform(get("/api/user")
                .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-user",
                        responseFields(
                                fieldWithPath("content[0].id").description("유저 id"),
                                fieldWithPath("content[0].name").description("유저 이름"),
                                fieldWithPath("content[0].grade").description("유저 학년"),
                                fieldWithPath("content[0].sex").description("유저 성별"),
                                fieldWithPath("content[0].duty").description("유저 직분"),
                                fieldWithPath("content[0].role").description("유저 역할"),
                                fieldWithPath("content[0].phoneNumber").description("유저 핸드폰"),
                                fieldWithPath("content[0].birth").description("유저 생일"),
                                fieldWithPath("content[0].isActive").description("유저 재적 여부"),
                                fieldWithPath("content[0].visitDate").description("유저 방문일"),
                                fieldWithPath("content[0].etc").description("유저 비고"),
                                fieldWithPath("content[0].updatedAt").description("수정시간"),
                                fieldWithPath("content[0].updatedBy").description("수정자")
                        )));
    }

    @DisplayName("[docs] GET /api/user/{userId}")
    @Test
    void findById() throws Exception {
        given(userQueryRepository.findById(any())).willReturn(
                new User(1L,
                        "우상욱",
                        9,
                        Sex.MALE,
                        Duty.MEMBER,
                        Role.MANAGER,
                        "01030387440",
                        LocalDate.of(1996, 10, 16),
                        true,
                        LocalDate.of(2016, 12, 2),
                        "",
                        LocalDateTime.now(),
                        "이종민"
                )
        );

        ResultActions actions = mockMvc.perform(
                get("/api/user/{userId}", 1L)
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-user-userId",
                        pathParameters(
                                parameterWithName("userId").description("유저 id")
                        ),
                        responseFields(
                                fieldWithPath("content.id").description("유저 id"),
                                fieldWithPath("content.name").description("유저 이름"),
                                fieldWithPath("content.grade").description("유저 학년"),
                                fieldWithPath("content.sex").description("유저 성별"),
                                fieldWithPath("content.duty").description("유저 직분"),
                                fieldWithPath("content.role").description("유저 역할"),
                                fieldWithPath("content.phoneNumber").description("유저 핸드폰"),
                                fieldWithPath("content.birth").description("유저 생일"),
                                fieldWithPath("content.isActive").description("유저 재적 여부"),
                                fieldWithPath("content.visitDate").description("유저 방문일"),
                                fieldWithPath("content.etc").description("유저 비고").optional(),
                                fieldWithPath("content.updatedAt").description("수정시간").optional(),
                                fieldWithPath("content.updatedBy").description("수정자").optional()
                        )));
    }

    @DisplayName("[docs] GET /api/user/myInfo")
    @Test
    void myInfo() throws Exception {
        given(userQueryRepository.findById(any())).willReturn(
                new User(1L,
                        "우상욱",
                        9,
                        Sex.MALE,
                        Duty.MEMBER,
                        Role.MANAGER,
                        "01030387440",
                        LocalDate.of(1996, 10, 16),
                        true,
                        LocalDate.of(2016, 12, 2),
                        "",
                        LocalDateTime.now(),
                        "이종민"
                )
        );

        ResultActions actions = mockMvc.perform(
                get("/api/user/myInfo")
                        .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-api-user-myInfo",
                        responseFields(
                                fieldWithPath("content.id").description("유저 id"),
                                fieldWithPath("content.name").description("유저 이름"),
                                fieldWithPath("content.grade").description("유저 학년"),
                                fieldWithPath("content.sex").description("유저 성별"),
                                fieldWithPath("content.duty").description("유저 직분"),
                                fieldWithPath("content.role").description("유저 역할"),
                                fieldWithPath("content.phoneNumber").description("유저 핸드폰"),
                                fieldWithPath("content.birth").description("유저 생일"),
                                fieldWithPath("content.isActive").description("유저 재적 여부"),
                                fieldWithPath("content.visitDate").description("유저 방문일"),
                                fieldWithPath("content.etc").description("유저 비고").optional(),
                                fieldWithPath("content.updatedAt").description("수정시간").optional(),
                                fieldWithPath("content.updatedBy").description("수정자").optional()
                        )));
    }
}
