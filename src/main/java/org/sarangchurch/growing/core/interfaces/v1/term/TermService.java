package org.sarangchurch.growing.core.interfaces.v1.term;

import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import java.util.List;
import java.util.Optional;

public interface TermService {
    Term findTerm(Long id);
    void emitByUserIds(List<Long> userIds);
    boolean areValidStumpUserIds(List<Long> userIds, Long termId);
    void processLineUps(
            StumpLineUp stumpLineUp,
            List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps,
            List<SmallGroupMemberLineUp> smallGroupMemberLineUps
    );
    Optional<Term> findLineUpTerm();
}
