package org.sarangchurch.growing.__e2e__.user.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.user.UserAssertions;
import org.sarangchurch.growing.__e2e__.user.UserSteps;

public class FindMyInfoTest extends AcceptanceTest {
    @DisplayName("[e2e] 내 정보를 조회할 수 있다.")
    @Test
    void findMyInfo() {
        UserAssertions.내_정보가_존재한다(UserSteps.상욱이가_자기_정보를_조회한다());
    }
}
