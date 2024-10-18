package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupLeaderFinder;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AssignValidator {
    private final CodyFinder codyFinder;
    private final SmallGroupLeaderFinder smallGroupLeaderFinder;

    public void validateAssignable(Term term, User user) {
        // 활성화
        if (!user.isActive()) {
            throw new IllegalStateException("비활성 유저입니다.");
        }

        // 코디
        boolean codyExists = codyFinder.existsByUserIdAndTermId(user.getId(), term.getId());

        if (codyExists) {
            throw new IllegalStateException("해당 텀에 코디로 배정된 유저입니다.");
        }

        // 일반 순장
        boolean smallGroupLeaderExists = smallGroupLeaderFinder.existsByUserIdAndTermId(user.getId(), term.getId());

        if (smallGroupLeaderExists) {
            throw new IllegalStateException("해당 텀에 일반 순장으로 배정된 유저입니다.");
        }

        // 일반 순원

        // 새가족 순장

        // 새가족 순원

        // 새가족
    }

    public void validateAssignable(Term term, List<User> users) {
        // 활성화

        // 코디

        // 일반 순장

        // 일반 순원

        // 새가족 순장

        // 새가족 순원

        // 새가족
    }
}
