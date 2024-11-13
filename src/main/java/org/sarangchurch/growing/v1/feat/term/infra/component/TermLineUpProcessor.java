package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupleaderlineup.SmallGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup.SmallGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.pastor.PastorWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupleader.SmallGroupLeaderFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupleader.SmallGroupLeaderWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TermLineUpProcessor {
    private final CodyWriter codyWriter;
    private final PastorWriter pastorWriter;
    private final SmallGroupLeaderWriter smallGroupLeaderWriter;
    private final SmallGroupWriter smallGroupWriter;
    private final SmallGroupLeaderFinder smallGroupLeaderFinder;
    private final SmallGroupMemberWriter smallGroupMemberWriter;

    @Transactional
    public void process(
            StumpLineUp stumpLineUp,
            List<SmallGroupLeaderLineUp> leaderLineUps,
            List<SmallGroupMemberLineUp> memberLineUps
    ) {
        processPastors(stumpLineUp);
        List<Cody> codies = processCodies(stumpLineUp);

        List<SmallGroup> smallGroups = processLeaderLineUps(leaderLineUps, codies);
        List<SmallGroupLeader> smallGroupLeaders = smallGroupLeaderFinder.findByTermId(stumpLineUp.getTermId());

        for (SmallGroupMemberLineUp memberLineUp : memberLineUps) {
            this.processMemberLineUp(memberLineUp, leaderLineUps, smallGroups, smallGroupLeaders);
        }
    }

    private void processMemberLineUp(
            SmallGroupMemberLineUp memberLineUp,
            List<SmallGroupLeaderLineUp> leaderLineUps,
            List<SmallGroup> smallGroups,
            List<SmallGroupLeader> smallGroupLeaders
    ) {
        Long leaderLineUpId = memberLineUp.getSmallGroupLeaderLineUpId();

        SmallGroupLeaderLineUp leaderLineUp = leaderLineUps.stream()
                .filter(it -> it.getId().equals(leaderLineUpId))
                .findAny()
                .orElseThrow();

        Long leaderUserId = leaderLineUp.getLeaderUserId();

        SmallGroupLeader smallGroupLeader = smallGroupLeaders.stream()
                .filter(it -> it.getUserId().equals(leaderUserId))
                .findAny()
                .orElseThrow();

        SmallGroup findSmallGroup = smallGroups.stream()
                .filter(smallGroup -> smallGroup.getSmallGroupLeaderId().equals(smallGroupLeader.getId()))
                .findAny()
                .orElseThrow();

        SmallGroupMember groupMember = SmallGroupMember.builder()
                .termId(memberLineUp.getTermId())
                .smallGroupId(findSmallGroup.getId())
                .userId(memberLineUp.getMemberUserId())
                .build();

        smallGroupMemberWriter.save(groupMember);
    }

    private List<SmallGroup> processLeaderLineUps(List<SmallGroupLeaderLineUp> leaderLineUps, List<Cody> codies) {
        List<SmallGroup> smallGroups = new ArrayList<>();

        for (SmallGroupLeaderLineUp leaderLineUp : leaderLineUps) {
            smallGroups.add(this.processLeaderLineUp(leaderLineUp, codies));
        }

        return smallGroups;
    }

    private SmallGroup processLeaderLineUp(SmallGroupLeaderLineUp lineUp, List<Cody> codies) {
        Long termId = lineUp.getTermId();
        Long leaderUserId = lineUp.getLeaderUserId();
        Long codyUserId = lineUp.getCodyUserId();

        SmallGroupLeader smallGroupLeader = SmallGroupLeader.builder()
                .termId(termId)
                .userId(leaderUserId)
                .build();

        SmallGroupLeader savedSmallGroupLeader = smallGroupLeaderWriter.save(smallGroupLeader);

        Cody cody = codies.stream()
                .filter(it -> it.getUserId().equals(codyUserId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));

        SmallGroup smallGroup = SmallGroup.builder()
                .termId(termId)
                .codyId(cody.getId())
                .smallGroupLeaderId(savedSmallGroupLeader.getId())
                .build();

        return smallGroupWriter.save(smallGroup);
    }

    private void processPastors(StumpLineUp stumpLineUp) {
        pastorWriter.save(
                Pastor.builder()
                        .termId(stumpLineUp.getTermId())
                        .userId(stumpLineUp.getSeniorPastorUserId())
                        .isSenior(true)
                        .build()
        );

        pastorWriter.saveAll(
                stumpLineUp
                        .getJuniorPastorUserIds()
                        .stream()
                        .map(it ->
                                Pastor.builder()
                                        .termId(stumpLineUp.getTermId())
                                        .userId(it)
                                        .isSenior(false)
                                        .build()
                        )
                        .collect(Collectors.toList())
        );
    }

    private List<Cody> processCodies(StumpLineUp stumpLineUp) {
        List<Cody> codies = stumpLineUp.getCodyUserIds()
                .stream()
                .map(it ->
                        Cody.builder()
                                .termId(stumpLineUp.getTermId())
                                .userId(it)
                                .build()
                )
                .collect(Collectors.toList());

        return codyWriter.saveAll(codies);
    }
}