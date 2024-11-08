package org.sarangchurch.growing.v1.feat.lineup.application.confirmlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpConfirmManager;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.LineUpTermFinder;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmLineUpService {
    private final LineUpTermFinder lineUpTermFinder;
    private final LineUpConfirmManager lineUpConfirmManager;

    public void confirm(Long termId) {
        Term term = lineUpTermFinder.findByIdOrThrow(termId);

        lineUpConfirmManager.confirm(term);
    }
}
