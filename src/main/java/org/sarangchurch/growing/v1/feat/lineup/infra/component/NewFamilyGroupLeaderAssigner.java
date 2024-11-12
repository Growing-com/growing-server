package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyLeaderLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final NormalLineUpAvailableValidator normalLineUpAvailableValidator;
    private final NewFamilyLeaderLineUpWriter newFamilyLeaderLineUpWriter;

    @Transactional
    public void assign(Term term, User codyUser, List<User> newFamilyGroupLeaderUsers) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() ->
                        stumpLineUpWriter.save(
                                StumpLineUp.builder()
                                        .termId(term.getId())
                                        .build()
                        )
                );

        if (!stumpLineUp.containsCody(codyUser)) {
            throw new IllegalStateException("코디가 아닌 지체에게 새가족 순장을 배정할 수 없습니다.");
        }

        newFamilyLeaderLineUpWriter.deleteByCodyUserIdAndTermId(codyUser.getId(), term.getId());

        newFamilyGroupLeaderUsers.forEach(user -> normalLineUpAvailableValidator.validateDutyAssignable(term, user, Duty.NEW_FAMILY_GROUP_LEADER));

        List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps = newFamilyGroupLeaderUsers.stream()
                .map(it ->
                        NewFamilyGroupLeaderLineUp.builder()
                                .termId(term.getId())
                                .codyUserId(codyUser.getId())
                                .leaderUserId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        newFamilyLeaderLineUpWriter.saveAll(newFamilyGroupLeaderLineUps);
    }
}
