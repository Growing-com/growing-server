package org.sarangchurch.growing.__doc__.attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sarangchurch.growing.__doc__.DocTest;
import org.sarangchurch.growing.attendance.application.dto.RegisterAttendanceRequest;
import org.sarangchurch.growing.attendance.application.facade.RegisterAttendanceFacade;
import org.sarangchurch.growing.attendance.domain.attendance.AttendanceStatus;
import org.sarangchurch.growing.attendance.presentation.RegisterAttendanceController;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterAttendanceDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private RegisterAttendanceFacade registerAttendanceFacade;

    @InjectMocks
    private RegisterAttendanceController registerAttendanceController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(registerAttendanceController)
                .apply(documentationConfiguration(restDocumentation))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(
                        objectMapper
                ))
                .build();
    }

    @DisplayName("[docs] POST /api/attendance")
    @Test
    void name() throws Exception {
        BDDMockito.doNothing()
                .when(registerAttendanceFacade)
                .registerAttendance(any(), any());

        RegisterAttendanceRequest request = new RegisterAttendanceRequest(
                LocalDate.of(2023, 10, 1),
                List.of(new RegisterAttendanceRequest.AttendanceRequest(
                        1L,
                        AttendanceStatus.ATTEND,
                        1L,
                        "비고"
                ))
        );

        ResultActions actions = mockMvc.perform(
                post("/api/attendance")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-api-attendance",
                        requestFields(
                                fieldWithPath("week").description("주차"),
                                fieldWithPath("attendances[0].teamMemberId").description("순원 id"),
                                fieldWithPath("attendances[0].status").description("ATTEND/ABSENT/ONLINE"),
                                fieldWithPath("attendances[0].teamId").description("순모임 id"),
                                fieldWithPath("attendances[0].etc").description("비고").optional()
                        )
                ));
    }
}
