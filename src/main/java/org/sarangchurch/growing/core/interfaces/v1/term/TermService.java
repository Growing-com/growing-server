package org.sarangchurch.growing.core.interfaces.v1.term;

import com.mysema.commons.lang.Pair;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import java.util.List;

public interface TermService {
    Term findTerm(Long id);
    void emitByUserIds(List<Long> userIds);
    boolean areValidStumpUserIds(List<Long> userIds, Long termId);
    Pair<Term, Cody> findTermAndCodyBySmallGroupId(Long smallGroupId);
    Pair<Term, Cody> findTermAndCodyByNewFamilyGroupId(Long newFamilyGroupId);
    void startTerm(
            Long termId,
            StumpLineUp stumpLineUp,
            List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps,
            List<SmallGroupMemberLineUp> smallGroupMemberLineUps
    );
}
