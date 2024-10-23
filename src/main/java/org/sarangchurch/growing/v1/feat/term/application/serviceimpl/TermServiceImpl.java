package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.UserEmitManager;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {
    private final TermFinder termFinder;
    private final UserEmitManager userEmitManager;

    @Override
    public Term findTerm(Long id) {
        return termFinder.findById(id);
    }

    @Override
    @Transactional
    public void emitByUserIds(List<Long> userIds) {
        userEmitManager.emitByUserIds(userIds);
    }
}
