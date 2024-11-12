package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.core.interfaces.v1.term.TermService;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupFinder;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermActivator;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermLineUpProcessor;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.UserEmitManager;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {
    private final TermFinder termFinder;
    private final UserEmitManager userEmitManager;
    private final TermValidator termValidator;
    private final SmallGroupFinder smallGroupFinder;
    private final CodyFinder codyFinder;
    private final NewFamilyGroupFinder newFamilyGroupFinder;
    private final TermLineUpProcessor termLineUpProcessor;
    private final TermActivator termActivator;

    @Override
    public Term findTerm(Long id) {
        return termFinder.findById(id);
    }

    @Override
    public void emitByUserIds(List<Long> userIds) {
        userEmitManager.emitByUserIds(userIds);
    }

    @Override
    public boolean areValidStumpUserIds(List<Long> userIds, Long termId) {
        return termValidator.areValidStumpUserIds(userIds, termId);
    }

    @Override
    public Pair<Term, Cody> findTermAndCodyBySmallGroupId(Long smallGroupId) {
        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(smallGroupId);
        Term term = termFinder.findById(smallGroup.getTermId());
        Cody cody = codyFinder.findByIdOrThrow(smallGroup.getCodyId());

        return Pair.of(term, cody);
    }

    @Override
    public Pair<Term, Cody> findTermAndCodyByNewFamilyGroupId(Long newFamilyGroupId) {
        NewFamilyGroup newFamilyGroup = newFamilyGroupFinder.findByIdOrThrow(newFamilyGroupId);
        Term term = termFinder.findById(newFamilyGroup.getTermId());
        Cody cody = codyFinder.findByIdOrThrow(newFamilyGroup.getCodyId());

        return Pair.of(term, cody);
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
}
