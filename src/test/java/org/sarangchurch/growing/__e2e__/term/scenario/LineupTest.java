package org.sarangchurch.growing.__e2e__.term.scenario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sarangchurch.growing.__e2e__.AcceptanceTest;
import org.sarangchurch.growing.__e2e__.CommonAssertions;
import org.sarangchurch.growing.__e2e__.term.TermSteps;

public class LineupTest extends AcceptanceTest {

    @DisplayName("[e2e] 새가족을 등반 시킬 수 있다.")
    @Test
    void name() {
        long 조예원_새가족반 = 38L;
        long 강라임_새가족 = 453L;

        CommonAssertions.요청에_성공한다(TermSteps.목사님이_등반을_요청한다(조예원_새가족반, 강라임_새가족));
    }

    @DisplayName("[e2e] 새가족을 두 번 등반 시킬 수 없다.")
    @Test
    void name2() {
        long 조예원_새가족반 = 38L;
        long 강라임_새가족 = 453L;

        CommonAssertions.요청에_성공한다(TermSteps.목사님이_등반을_요청한다(조예원_새가족반, 강라임_새가족));
        CommonAssertions.요청에_실패한다(TermSteps.목사님이_등반을_요청한다(조예원_새가족반, 강라임_새가족));
    }
}
