package org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUpReader;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup.NewFamilyLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily.NewFamilyDownstream;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyAssigner {
    private final NewFamilyGroupLeaderLineUpReader newFamilyGroupLeaderLineUpReader;
    private final NewFamilyLineUpWriter newFamilyLineUpWriter;
    private final NewFamilyDownstream newFamilyDownstream;

    @Transactional
    public void assign(Term term, User leaderUser, List<NewFamily> newFamilies) {
        NewFamilyGroupLeaderLineUp newFamilyGroupLeaderLineUp = newFamilyGroupLeaderLineUpReader.findByLeaderUserIdAndTermId(
                leaderUser.getId(),
                term.getId()
        );

        newFamilyLineUpWriter.deleteByNewFamilyGroupLeaderLineUpIdAndTermId(newFamilyGroupLeaderLineUp.getId(), term.getId());

        boolean areValidNewFamilies = newFamilies.stream()
                .allMatch(newFamily -> newFamilyDownstream.isNewFamilyByUserId(newFamily.getUserId()));

        if (!areValidNewFamilies) {
            throw new IllegalStateException("새가족이 아닌 지체가 포함되어 있습니다. 일반 라인업을 진행해주세요.");
        }

        List<NewFamilyLineUp> newFamilyLineUps = newFamilies.stream()
                .map(it ->
                        NewFamilyLineUp.builder()
                                .termId(term.getId())
                                .newFamilyGroupLeaderLineUpId(newFamilyGroupLeaderLineUp.getId())
                                .newFamilyId(it.getId())
                                .build()
                )
                .collect(Collectors.toList());

        newFamilyLineUpWriter.saveAll(newFamilyLineUps);
    }
}
