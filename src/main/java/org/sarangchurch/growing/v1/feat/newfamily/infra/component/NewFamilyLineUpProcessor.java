package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupleader.NewFamilyGroupLeaderFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpProcessor {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyGroupFinder newFamilyGroupFinder;
    private final NewFamilyGroupLeaderFinder newFamilyGroupLeaderFinder;

    @Transactional
    public void process(List<NewFamilyGroupLeaderLineUp> leaderLineUps, List<NewFamilyLineUp> newFamilyLineUps) {
        Long termId = newFamilyLineUps.get(0).getTermId();

        List<Long> newFamilyIds = newFamilyLineUps.stream()
                .map(NewFamilyLineUp::getNewFamilyId)
                .collect(Collectors.toList());

        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean isAllNewFamily = newFamilies.stream()
                .allMatch(it -> newFamilyFinder.isNewFamilyByUserId(it.getUserId()));

        if (!isAllNewFamily) {
            throw new IllegalStateException("등반하지 않은 새가족만 새가족 라인업할 수 있습니다.");
        }

        List<NewFamilyGroup> groups = newFamilyGroupFinder.findByTermId(termId);
        List<NewFamilyGroupLeader> groupLeaders = newFamilyGroupLeaderFinder.findByTermId(termId);

        for (NewFamilyLineUp newFamilyLineUp : newFamilyLineUps) {
            NewFamily newFamily = newFamilies.stream()
                    .filter(it -> it.getId().equals(newFamilyLineUp.getNewFamilyId()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("새가족이 존재하지 않습니다."));

            NewFamilyGroupLeaderLineUp lineUp = leaderLineUps.stream()
                    .filter(it -> it.getId().equals(newFamilyLineUp.getNewFamilyGroupLeaderLineUpId()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("새가족 순장 라인업 기록이 존재하지 않습니다."));

            NewFamilyGroupLeader groupLeader = groupLeaders.stream()
                    .filter(it -> it.getUserId().equals(lineUp.getLeaderUserId()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("새가족 순장이 존재하지 않습니다."));

            NewFamilyGroup group = groups.stream()
                    .filter(it -> it.getNewFamilyGroupLeaderId().equals(groupLeader.getId()))
                    .findAny()
                    .orElseThrow(() -> new IllegalStateException("새가족반이 존재하지 않습니다."));

            newFamily.updateNewFamilyGroup(group.getId());
        }
    }
}
