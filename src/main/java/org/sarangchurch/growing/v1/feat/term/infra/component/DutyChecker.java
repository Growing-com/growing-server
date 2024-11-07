package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Duty;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.pastor.PastorFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupleader.SmallGroupLeaderFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupLeaderDownstream;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupMemberDownstream;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DutyChecker {
    private final PastorFinder pastorFinder;
    private final CodyFinder codyFinder;
    private final SmallGroupLeaderFinder smallGroupLeaderFinder;
    private final SmallGroupMemberFinder smallGroupMemberFinder;
    private final NewFamilyGroupLeaderDownstream newFamilyGroupLeaderDownstream;
    private final NewFamilyGroupMemberDownstream newFamilyGroupMemberDownstream;
    private final NewFamilyDownstream newFamilyDownstream;

    public Duty findDutyByTermAndUserId(Term term, Long userId) {
        // 교역자
        if (pastorFinder.existsByUserIdAndTermId(userId, term.getId())) {
            return Duty.PASTOR;
        }

        // 코디
        if (codyFinder.existsByUserIdAndTermId(userId, term.getId())) {
            return Duty.CODY;
        }

        // 일반 순장
        if (smallGroupLeaderFinder.existsByUserIdAndTermId(userId, term.getId())) {
            return Duty.SMALL_GROUP_LEADER;
        }

        // 새가족 순장
        if (newFamilyGroupLeaderDownstream.existsByUserIdAndTermId(userId, term.getId())) {
            return Duty.NEW_FAMILY_GROUP_LEADER;
        }

        // 일반 순원
        if (smallGroupMemberFinder.existsByUserIdAndTermId(userId, term.getId())) {
            return Duty.SMALL_GROUP_MEMBER;
        }

        // 새가족 순원
        if (newFamilyGroupMemberDownstream.existsByUserIdAndTermId(userId, term.getId())) {
            return Duty.NEW_FAMILY_GROUP_MEMBER;
        }

        // 새가족
        if (newFamilyDownstream.isNewFamilyByUserId(userId)) {
            return Duty.NEW_FAMILY;
        }

        return Duty.NOT_PLACED;
    }
}
