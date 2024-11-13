package org.sarangchurch.growing.v1.feat.lineup.application.assignseniorpastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner.SeniorPastorAssigner;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignSeniorPastorService {
    private final LineUpTermFinder lineUpTermFinder;
    private final UserDownstream userDownstream;
    private final SeniorPastorAssigner seniorPastorAssigner;

    public void assign(Long termId, Long userId) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        User user = userDownstream.findActiveByIdOrThrow(userId);

        seniorPastorAssigner.assign(term, user);
    }
}
