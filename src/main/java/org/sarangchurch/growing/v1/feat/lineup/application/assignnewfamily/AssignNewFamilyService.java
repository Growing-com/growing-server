package org.sarangchurch.growing.v1.feat.lineup.application.assignnewfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.NewFamilyAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignNewFamilyService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final NewFamilyDownstream newFamilyDownstream;
    private final NewFamilyAssigner newFamilyAssigner;

    public void assign(Long termId, Long leaderUserId, List<Long> newFamilyIds) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        User leaderUser = userDownstream.findActiveByIdOrThrow(leaderUserId);
        List<NewFamily> newFamilies = newFamilyDownstream.findByIdInOrThrow(newFamilyIds);

        newFamilyAssigner.assign(term, leaderUser, newFamilies);
    }
}
