package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.core.interfaces.common.Events;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.term.domain.UsersEmitEvent;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.UserEmitManager;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.term.TermActivator;
import org.sarangchurch.growing.v1.feat.term.infra.component.term.TermLineUpProcessor;
import org.sarangchurch.growing.v1.feat.term.infra.component.term.TermValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {
    private final TermFinder termFinder;
    private final UserEmitManager userEmitManager;
    private final TermValidator termValidator;
    private final TermLineUpProcessor termLineUpProcessor;
    private final TermActivator termActivator;
    private final CodyValidator codyValidator;
    private final CodyFinder codyFinder;

    @Override
    public Term findTerm(Long id) {
        return termFinder.findById(id);
    }

    @Override
    @Transactional
    public void emitByUserIds(List<Long> userIds) {
        userEmitManager.emitByUserIds(userIds);
        Events.raise(new UsersEmitEvent(userIds));
    }

    @Override
    public boolean areValidCodyUserIdsByTermId(List<Long> userIds, Long termId) {
        return termValidator.areValidCodyUserIdsByTermId(userIds, termId);
    }

    @Override
    public void processLineUps(
            StumpLineUp stumpLineUp,
            List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps,
            List<SmallGroupMemberLineUp> smallGroupMemberLineUps
    ) {
        termLineUpProcessor.process(stumpLineUp, smallGroupLeaderLineUps, smallGroupMemberLineUps);
        termActivator.activate(stumpLineUp.getTermId());
    }

    @Override
    public Optional<Term> findLineUpTerm() {
        return termFinder.findLineUpTerm();
    }

    @Override
    public boolean areValidMemberUserIdsByCodyId(List<Long> userIds, Long codyId) {
        return codyValidator.areValidMemberUserIdsByCodyId(codyId, userIds);
    }

    @Override
    public boolean containsCodyByTermIdAndCodyId(Long termId, Long codyId) {
        Term term = termFinder.findById(termId);
        List<Cody> codies = codyFinder.findByTermId(term.getId());
        return codies.stream().anyMatch(it -> it.getId().equals(codyId));
    }

    @Override
    public Term findActiveTerm() {
        return termFinder.findActiveOrThrow();
    }
}
