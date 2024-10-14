package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupAppender;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSmallGroupService {
    private final TermValidator termValidator;
    private final CodyValidator codyValidator;
    private final SmallGroupAppender smallGroupAppender;

    public void create(Long termId, Long codyId, Long userId) {
        termValidator.validateActive(termId);
        codyValidator.validateAvailableByIdAndTerm(codyId, termId);
        smallGroupAppender.append(termId, codyId, userId);
    }
}
