package org.sarangchurch.growing.attendance.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @DisplayName("[unit] 어드민 여부를 검사한다.")
    @Test
    void isAdmin() {
        User user = new User(1L, true);

        assertThat(user.isAdmin()).isTrue();
    }
}