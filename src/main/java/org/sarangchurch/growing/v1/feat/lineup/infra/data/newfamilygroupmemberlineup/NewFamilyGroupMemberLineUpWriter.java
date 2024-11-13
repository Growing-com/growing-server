package org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilygroupmemberlineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupMemberLineUpWriter {
    private final NewFamilyGroupMemberLineUpRepository newFamilyGroupMemberLineUpRepository;

    public void deleteByNewFamilyGroupLeaderLineUpIdAndTermId(Long newFamilyGroupLeaderLineUpId, Long termId) {
        newFamilyGroupMemberLineUpRepository.deleteByNewFamilyGroupLeaderLineUpIdAndTermId(newFamilyGroupLeaderLineUpId, termId);
    }

    public void saveAll(List<NewFamilyGroupMemberLineUp> newFamilyGroupLeaderLineUps) {
        newFamilyGroupMemberLineUpRepository.saveAll(newFamilyGroupLeaderLineUps);
    }

    public void deleteByNewFamilyGroupLeaderLineUpIdIn(List<Long> deleteIds) {
        newFamilyGroupMemberLineUpRepository.deleteByNewFamilyGroupLeaderLineUpIdIn(deleteIds);
    }
}
