package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CodyAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final SmallGroupLeaderLineUpReader smallGroupLeaderLineUpReader;
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final NormalLineUpAvailableValidator normalLineUpAvailableValidator;

    @Transactional
    public void assign(Term term, List<User> codyUsers) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() ->
                        stumpLineUpWriter.save(
                                StumpLineUp.builder()
                                        .termId(term.getId())
                                        .build()
                        )
                );

        validateAssignedLeaderExists(term, codyUsers);

        codyUsers.forEach(user -> normalLineUpAvailableValidator.validateDutyAssignable(term, user, Duty.CODY));

        stumpLineUp.setCodies(codyUsers);
    }

    private void validateAssignedLeaderExists(Term term, List<User> codyUsers) {
        List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps = smallGroupLeaderLineUpReader.findByTermId(term.getId());

        for (SmallGroupLeaderLineUp smallGroupLeaderLineUp : smallGroupLeaderLineUps) {
            Optional<User> codyUserOptional = codyUsers.stream()
                    .filter(it -> it.getId().equals(smallGroupLeaderLineUp.getCodyUserId()))
                    .findAny();

            if (codyUserOptional.isEmpty()) {
                throw new IllegalStateException("담당하는 일반 순장이 있는 코디는 삭제할 수 없습니다.");
            }
        }

        List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps = newFamilyGroupLeaderLineUpReader.findByTermId(term.getId());

        for (NewFamilyGroupLeaderLineUp newFamilyGroupLeaderLineUp : newFamilyGroupLeaderLineUps) {
            Optional<User> codyUserOptional = codyUsers.stream()
                    .filter(it -> it.getId().equals(newFamilyGroupLeaderLineUp.getCodyUserId()))
                    .findAny();

            if (codyUserOptional.isEmpty()) {
                throw new IllegalStateException("담당하는 새가족 순장이 있는 코디는 삭제할 수 없습니다.");
            }
        }
    }
}
