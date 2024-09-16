package org.sarangchurch.growing.v1.core.interfaces.common;

import lombok.Getter;

import java.util.List;

@Getter
public class CursorBasedPageResponse<T> {
    private final List<T> content;
    private final boolean hasNextPage;

    public static <T> CursorBasedPageResponse<T> of(List<T> content, int limit) {
        int contentSize = content.size();

        if (contentSize > limit) {
            content.remove(contentSize - 1);
            return new CursorBasedPageResponse<>(content, true);
        }

        return new CursorBasedPageResponse<>(content, false);
    }

    private CursorBasedPageResponse(List<T> content, boolean hasNextPage) {
        this.content = content;
        this.hasNextPage = hasNextPage;
    }
}
