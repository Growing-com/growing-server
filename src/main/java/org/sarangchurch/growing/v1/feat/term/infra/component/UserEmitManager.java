package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupmember.NewFamilyGroupMemberWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyRemover;
import org.sarangchurch.growing.v1.feat.term.infra.component.pastor.PastorRemover;
import org.sarangchurch.growing.v1.feat.term.infra.component.term.TermDutyChecker;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserEmitManager {
    private final TermFinder termFinder;
    private final TermDutyChecker termDutyChecker;
    private final PastorRemover pastorRemover;
    private final CodyRemover codyRemover;
    private final SmallGroupMemberWriter smallGroupMemberWriter;
    private final NewFamilyGroupMemberWriter newFamilyGroupMemberWriter;

    public void emitByUserIds(List<Long> userIds) {
        Term activeTerm = termFinder.findActiveOrThrow();
        Long termId = activeTerm.getId();

        for (Long userId : userIds) {
            Duty duty = termDutyChecker.findDutyByTermAndUserId(activeTerm, userId);

            switch (duty) {
                case PASTOR:
                    pastorRemover.removeByUserIdAndTermId(userId, termId);
                    break;

                case CODY:
                    codyRemover.removeByUserIdAndTermId(userId, termId);
                    break;

                case SMALL_GROUP_MEMBER:
                    smallGroupMemberWriter.deleteByUserIdAndTermId(userId, termId);
                    break;

                case NEW_FAMILY_GROUP_MEMBER:
                    newFamilyGroupMemberWriter.deleteByUserIdAndTermId(userId, termId);
                    break;

                case SMALL_GROUP_LEADER:
                case NEW_FAMILY_GROUP_LEADER:
                    throw new IllegalStateException("현재 텀의 일반 혹은 새가족 순장을 방출할 수 없습니다.");

                case NEW_FAMILY:
                    throw new IllegalStateException("새가족을 방출할 수 없습니다.");

                case NOT_PLACED:
                default:
                    break;
            }
        }
    }
}
