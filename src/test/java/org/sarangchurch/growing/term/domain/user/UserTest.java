package org.sarangchurch.growing.term.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("[unit] 유저를 라인아웃 시킨다.")
    @Test
    void lineout() {
        User user = new User(1L, true);

        user.lineout();

        assertThat(user.getIsActive()).isFalse();
    }
}