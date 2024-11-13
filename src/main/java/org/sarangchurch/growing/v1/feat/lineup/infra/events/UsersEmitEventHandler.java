package org.sarangchurch.growing.v1.feat.lineup.infra.events;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup.NewFamilyLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupleaderlineup.SmallGroupLeaderLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.smallgroupmemberlineup.SmallGroupMemberLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.term.TermDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.UsersEmitEvent;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsersEmitEventHandler {
    private final TermDownstream termDownstream;
    private final StumpLineUpFinder stumpLineUpFinder;
    private final SmallGroupLeaderLineUpReader smallGroupLeaderLineUpReader;
    private final SmallGroupLeaderLineUpWriter smallGroupLeaderLineUpWriter;
    private final SmallGroupMemberLineUpWriter smallGroupMemberLineUpWriter;
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final NewFamilyGroupLeaderLineUpWriter newFamilyGroupLeaderLineUpWriter;
    private final NewFamilyGroupMemberLineUpWriter newFamilyGroupMemberLineUpWriter;
    private final NewFamilyLineUpWriter newFamilyLineUpWriter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void on(UsersEmitEvent event) {
        Optional<Term> termOptional = termDownstream.findLineUpTerm();

        if (termOptional.isEmpty()) {
            return;
        }

        List<Long> userIds = event.getUserIds();
        Term lineUpTerm = termOptional.get();
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermIdOrThrow(lineUpTerm.getId());

        stumpLineUp.removeByUserIds(userIds);
        handleSmallGroupLineUps(lineUpTerm, userIds);
        handleNewFamilyGroupLineUps(lineUpTerm, userIds);
    }

    private void handleSmallGroupLineUps(Term lineUpTerm, List<Long> userIds) {
        List<SmallGroupLeaderLineUp> smallGroupLeaderLineUps = smallGroupLeaderLineUpReader
                .findByTermId(lineUpTerm.getId());

        List<SmallGroupLeaderLineUp> deleteSmallGroupLeaderLineUps = smallGroupLeaderLineUps.stream()
                .filter(it -> it.containsUserByUserIds(userIds))
                .collect(Collectors.toList());

        List<Long> deleteIds = deleteSmallGroupLeaderLineUps.stream().map(SmallGroupLeaderLineUp::getId).collect(Collectors.toList());

        smallGroupLeaderLineUpWriter.deleteByIdIn(deleteIds);
        smallGroupMemberLineUpWriter.deleteBySmallGroupLeaderLineUpIdIn(deleteIds);
    }

    private void handleNewFamilyGroupLineUps(Term lineUpTerm, List<Long> userIds) {
        List<NewFamilyGroupLeaderLineUp> newFamilyGroupLeaderLineUps = newFamilyGroupLeaderLineUpReader
                .findByTermId(lineUpTerm.getId());

        List<NewFamilyGroupLeaderLineUp> deleteNewFamilyGroupLeaderLineUps = newFamilyGroupLeaderLineUps.stream()
                .filter(it -> it.containsUserByUserIds(userIds))
                .collect(Collectors.toList());

        List<Long> deleteIds = deleteNewFamilyGroupLeaderLineUps.stream().map(NewFamilyGroupLeaderLineUp::getId).collect(Collectors.toList());

        newFamilyGroupLeaderLineUpWriter.deleteByIdIn(deleteIds);
        newFamilyGroupMemberLineUpWriter.deleteByNewFamilyGroupLeaderLineUpIdIn(deleteIds);
        newFamilyLineUpWriter.deleteByNewFamilyGroupLeaderLineUpIdIn(deleteIds);
    }
}
