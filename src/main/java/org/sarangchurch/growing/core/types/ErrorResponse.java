package org.sarangchurch.growing.core.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Builder
    public ErrorResponse(int status, String message, List<ValidationError> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    @Getter
    public static class ValidationError {
        private final String field;
        private final String reason;

        public ValidationError(String field, String message) {
            this.field = field;
            this.reason = message;
        }
    }
}
