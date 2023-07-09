package com.moim.backend.docs.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moim.backend.RestDocsSupport;
import com.moim.backend.domain.admin.controller.VersionController;
import com.moim.backend.domain.admin.request.VersionRequest;
import com.moim.backend.domain.admin.response.VersionResponse;
import com.moim.backend.domain.admin.service.VersionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VersionControllerDocsTest extends RestDocsSupport {

    private final VersionService versionService = mock(VersionService.class);

    @Override
    protected Object initController() {
        return new VersionController(versionService);
    }

    @DisplayName("관리자 버전 변경 API")
    @Test
    void test() throws Exception {
        // given
        VersionRequest.Update request = new VersionRequest.Update("version 0.0.1", "모이닷 관리자");

        given(versionService.updateServiceVersion(any()))
                .willReturn(VersionResponse.Update.builder()
                        .version("version 0.0.1")
                        .updateAdminName("모이닷 관리자")
                        .build()
                );

        // when // then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/version")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("version-check",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("version").type(JsonFieldType.STRING)
                                        .description("버전 이름"),
                                fieldWithPath("updateAdminName").type(JsonFieldType.STRING)
                                        .description("관리자 이름")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("상태 메세지"),
                                fieldWithPath("data.version").type(JsonFieldType.STRING)
                                        .description("버전 이름"),
                                fieldWithPath("data.updateAdminName").type(JsonFieldType.STRING)
                                        .description("관리자 이름")
                        )
                ));
    }
}
