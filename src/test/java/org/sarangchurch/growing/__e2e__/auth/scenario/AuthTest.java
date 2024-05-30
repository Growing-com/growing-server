package org.sarangchurch.growing.__e2e__.auth.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.CommonAssertions;
import org.sarangchurch.growing.__e2e__.auth.AuthAssertions;
import org.sarangchurch.growing.__e2e__.auth.AuthSteps;

class AuthTest extends AcceptanceTest {

    @DisplayName("[e2e] 로그인 여부를 확인한다.")
    @Test
    void checkLoginStatus() {
        AuthAssertions.로그인이_안되어있다(AuthSteps.로그인_여부를_확인한다());
    }

    @DisplayName("[e2e] 로그인에 성공한다.")
    @Test
    void name() {
        CommonAssertions.요청에_성공한다(AuthSteps.로그인한다("soonjong8", "leader"));
    }

    @DisplayName("[e2e] 로그인에 실패한다.")
    @Test
    void name2() {
        CommonAssertions.요청에_실패한다(AuthSteps.로그인한다("foobar", "baz"));
    }
}
