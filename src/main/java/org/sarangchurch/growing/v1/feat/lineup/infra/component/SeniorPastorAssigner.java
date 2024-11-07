package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SeniorPastorAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;

    @Transactional
    public void assign(Term term, Long userId) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseThrow(() -> new IllegalArgumentException("그루터기 라인업이 존재하지 않습니다."));

        stumpLineUp.changeSeniorPastor(userId);
    }
}
