package org.sarangchurch.growing.v2.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.term.TermService;
import org.sarangchurch.growing.v2.feat.term.domain.term.Term;
import org.sarangchurch.growing.v2.feat.term.infrastructure.component.LineupManager;
import org.sarangchurch.growing.v2.feat.term.infrastructure.component.TermFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {
    private final TermFinder termFinder;
    private final LineupManager lineupManager;

    @Override
    public void lineupUser(Long userId, Long smallGroupId) {
        lineupManager.lineup(userId, smallGroupId);
    }

    @Override
    public Term findTermBySmallGroupId(Long smallGroupId) {
        return termFinder.findBySmallGroupId(smallGroupId);
    }

    @Override
    public Term findTerm(Long id) {
        return termFinder.findById(id);
    }
}
