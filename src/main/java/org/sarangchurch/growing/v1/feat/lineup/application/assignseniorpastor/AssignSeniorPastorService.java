package org.sarangchurch.growing.v1.feat.lineup.application.assignseniorpastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpAvailableValidator;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.SeniorPastorAssigner;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignSeniorPastorService {
    private final LineUpTermFinder lineUpTermFinder;
    private final LineUpAvailableValidator lineUpAvailableValidator;
    private final SeniorPastorAssigner seniorPastorAssigner;

    @Transactional
    public void assign(Long termId, Long userId) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);
        lineUpAvailableValidator.validateAvailable(term, userId);
        seniorPastorAssigner.assign(term, userId);
    }
}
