package org.sarangchurch.growing.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {

    @DisplayName("[unit] 이름을 비교한다.")
    @Test
    void mustMatchName() {
        UserEntity user = UserEntity.builder()
                .name("우상욱")
                .build();

        assertThat(user.matchName("우상욱")).isTrue();
        assertThat(user.matchName("이종민")).isFalse();
    }
}