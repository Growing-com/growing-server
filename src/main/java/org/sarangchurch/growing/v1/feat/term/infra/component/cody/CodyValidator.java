package org.sarangchurch.growing.v1.feat.term.infra.component.cody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodyValidator {
    private final CodyFinder codyFinder;
    private final TermFinder termFinder;

    public void validateAvailableById(Long codyId) {
        Cody cody = codyFinder.findByIdOrThrow(codyId);
        termFinder.findActiveByIdOrThrow(cody.getTermId());
    }
}
