package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupmember.NewFamilyGroupMember;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupmember.NewFamilyGroupMemberWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.CodyDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLineUpProcessor {
    private final NewFamilyGroupWriter newFamilyGroupWriter;
    private final CodyDownstream codyDownstream;
    private final NewFamilyGroupMemberWriter newFamilyGroupMemberWriter;

    @Transactional
    public void process(
            List<NewFamilyGroupLeaderLineUp> leaderLineUps,
            List<NewFamilyGroupMemberLineUp> memberLineUps
    ) {
        Long termId = leaderLineUps.get(0).getTermId();
        List<Cody> codies = codyDownstream.findByTermId(termId);

        List<NewFamilyGroup> newFamilyGroups = processLeaderLineUps(leaderLineUps, codies);

        for (NewFamilyGroupMemberLineUp memberLineUp : memberLineUps) {
            this.processMemberLineUp(memberLineUp, leaderLineUps, newFamilyGroups);
        }
    }

    private List<NewFamilyGroup> processLeaderLineUps(List<NewFamilyGroupLeaderLineUp> leaderLineUps, List<Cody> codies) {
        List<NewFamilyGroup> newFamilyGroups = new ArrayList<>();

        for (NewFamilyGroupLeaderLineUp leaderLineUp : leaderLineUps) {
            newFamilyGroups.add(this.processLeaderLineUp(leaderLineUp, codies));
        }

        return newFamilyGroups;
    }

    private NewFamilyGroup processLeaderLineUp(NewFamilyGroupLeaderLineUp lineUp, List<Cody> codies) {
        Long termId = lineUp.getTermId();
        Long leaderUserId = lineUp.getLeaderUserId();
        Long codyUserId = lineUp.getCodyUserId();

        Cody cody = codies.stream()
                .filter(it -> it.getUserId().equals(codyUserId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));

        NewFamilyGroup newFamilyGroup = NewFamilyGroup.builder()
                .termId(termId)
                .codyId(cody.getId())
                .leaderUserId(leaderUserId)
                .build();

        return newFamilyGroupWriter.save(newFamilyGroup);
    }

    private void processMemberLineUp(
            NewFamilyGroupMemberLineUp memberLineUp,
            List<NewFamilyGroupLeaderLineUp> leaderLineUps,
            List<NewFamilyGroup> newFamilyGroups
    ) {
        Long leaderLineUpId = memberLineUp.getNewFamilyGroupLeaderLineUpId();

        NewFamilyGroupLeaderLineUp leaderLineUp = leaderLineUps.stream()
                .filter(it -> it.getId().equals(leaderLineUpId))
                .findAny()
                .orElseThrow();

        Long leaderUserId = leaderLineUp.getLeaderUserId();

        NewFamilyGroup findNewFamilyGroup = newFamilyGroups.stream()
                .filter(newFamilyGroup -> newFamilyGroup.getLeaderUserId().equals(leaderUserId))
                .findAny()
                .orElseThrow();

        NewFamilyGroupMember groupMember = NewFamilyGroupMember.builder()
                .termId(memberLineUp.getTermId())
                .newFamilyGroupId(findNewFamilyGroup.getId())
                .userId(memberLineUp.getMemberUserId())
                .build();

        newFamilyGroupMemberWriter.save(groupMember);
    }
}
