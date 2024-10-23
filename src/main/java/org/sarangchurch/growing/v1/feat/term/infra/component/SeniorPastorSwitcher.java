package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.data.PastorFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SeniorPastorSwitcher {
    private final TermFinder termFinder;
    private final PastorFinder pastorFinder;

    @Transactional
    public void run(Long termId, Long targetSeniorPastorId) {
        Term term = termFinder.findActiveByIdOrThrow(termId);

        Pastor beforeSeniorPastor =  pastorFinder.findSeniorByTermIdOrThrow(termId);
        Pastor targetSeniorPastor = pastorFinder.findByIdOrThrow(targetSeniorPastorId);

        if (!targetSeniorPastor.isSameTerm(term)) {
            throw new IllegalStateException("다음 담임 교역자는 해당 텀에 소속되어 있어야합니다.");
        }

        if (!targetSeniorPastor.isSameTerm(beforeSeniorPastor)) {
            throw new IllegalStateException("텀의 현재 담임 교역자와 다음 교역자의 소속된 텀이 일치하지 않습니다.");
        }

        beforeSeniorPastor.toJunior();
        targetSeniorPastor.toSenior();
    }
}
