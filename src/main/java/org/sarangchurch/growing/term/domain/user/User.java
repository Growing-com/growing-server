package org.sarangchurch.growing.term.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {
    private final Long id;
    private Boolean isActive;

    public User(Long id, Boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }

    public void lineout() {
        isActive = false;
    }
}
