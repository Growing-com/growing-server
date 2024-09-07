package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {
    private final TermFinder termFinder;

    @Override
    public Term findTerm(Long id) {
        return termFinder.findById(id);
    }
}
