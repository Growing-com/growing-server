package org.sarangchurch.growing.__e2e__.user.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.CommonAssertions;
import org.sarangchurch.growing.__e2e__.user.UserAssertions;
import org.sarangchurch.growing.__e2e__.user.UserSteps;

public class FindAllUserTest extends AcceptanceTest {
    @DisplayName("[e2e] 관리자는 유저를 전체 조회할 수 있다.")
    @Test
    void findAllUsersByAdmin() {
        UserAssertions.유저수가_일치한다(UserSteps.교역자가_유저를_전체_조회한다(), 453);
    }

    @DisplayName("[e2e] 매니저가 유저를 전체 조회할 수 없다.")
    @Test
    void findAllUsersByManager() {
        CommonAssertions.권한_검사에_실패한다(UserSteps.민섭이가_유저를_전체_조회한다());
    }

    @DisplayName("[e2e] 유저는 유저를 전체 조회할 수 없다.")
    @Test
    void findAllUsersByUser() {
        CommonAssertions.권한_검사에_실패한다(UserSteps.상욱이가_유저를_전체_조회한다());
    }
}
