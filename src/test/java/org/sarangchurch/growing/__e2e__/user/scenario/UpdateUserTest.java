package org.sarangchurch.growing.__e2e__.user.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.CommonAssertions;
import org.sarangchurch.growing.__e2e__.user.UserAssertions;
import org.sarangchurch.growing.__e2e__.user.UserSteps;

public class UpdateUserTest extends AcceptanceTest {

    @DisplayName("[e2e] 유저 이름을 수정할 때 겹치는 이름으로 수정할 수 없다.")
    @Test
    void name() {
        Long 오정은 = 4L;

        UserAssertions.유저_수정에_실패한다(UserSteps.교역자가_유저의_이름을_수정한다(오정은, "오준석"));
    }

    @DisplayName("[e2e] 유저 이름 수정에 성공한다.")
    @Test
    void name2() {
        Long 오정은 = 4L;

        CommonAssertions.요청에_성공한다(UserSteps.교역자가_유저의_이름을_수정한다(오정은, "오정은B"));
    }
}
