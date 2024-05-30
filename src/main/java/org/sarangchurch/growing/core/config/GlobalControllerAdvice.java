package org.sarangchurch.growing.core.config;

import org.sarangchurch.growing.core.types.ErrorResponse;
import org.sarangchurch.growing.core.types.ErrorResponse.ValidationError;
import org.sarangchurch.growing.core.types.ForbiddenException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            EntityNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<ErrorResponse> handle400(Exception e) {
        return createErrorResponse(400, e);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenException(ForbiddenException e) {
        return createErrorResponse(403, e);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(int status, Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status)
                .message(e.getMessage())
                .errors(Collections.emptyList())
                .build();

        return ResponseEntity.status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(it -> new ValidationError(it.getField(), it.getDefaultMessage()))
                .collect(Collectors.toList());

        String errorMessage = validationErrors.get(0).getReason();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(400)
                .message(errorMessage)
                .errors(validationErrors)
                .build();

        return ResponseEntity.status(400)
                .body(errorResponse);
    }
}
