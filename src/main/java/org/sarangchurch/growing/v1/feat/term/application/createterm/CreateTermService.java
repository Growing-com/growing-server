package org.sarangchurch.growing.v1.feat.term.application.createterm;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.term.TermAppender;
import org.sarangchurch.growing.v1.feat.term.infra.component.term.TermValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTermService {
    private final TermValidator termValidator;
    private final TermAppender termAppender;

    public void create(CreateTermRequest request) {
        termValidator.validateLineUpNotPresent();
        termAppender.append(request.toEntity());
    }
}
