package org.sarangchurch.growing.core.types;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final T content;

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    private ApiResponse(T content) {
        this.content = content;
    }
}