package org.sarangchurch.growing.__e2e__.term.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.CommonAssertions;
import org.sarangchurch.growing.__e2e__.term.TermAssertions;
import org.sarangchurch.growing.__e2e__.term.TermSteps;

public class RegisterNewComerTest extends AcceptanceTest {

    @DisplayName("[e2e] 중복된 이름으로 새가족을 등록할 수 없다.")
    @Test
    void name() {
        CommonAssertions.요청에_성공한다(TermSteps.목사님이_새가족을_등록한다("김아무개"));
        TermAssertions.중복_이름으로_새가족을_등록할_수_없다(TermSteps.목사님이_새가족을_등록한다("김아무개"));
    }
}
