package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupleaderlineup.NewFamilyGroupLeaderLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilygroupmemberlineup.NewFamilyGroupMemberLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupLineUpProcessor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewFamilyGroupServiceImpl implements NewFamilyGroupService {
    private final NewFamilyGroupFinder newFamilyGroupFinder;
    private final NewFamilyGroupLineUpProcessor newFamilyGroupLineUpProcessor;

    @Override
    public long countByCodyId(Long codyId) {
        return newFamilyGroupFinder.countByCodyId(codyId);
    }

    @Override
    public NewFamilyGroup findByIdOrThrow(Long newFamilyGroupId) {
        return newFamilyGroupFinder.findByIdOrThrow(newFamilyGroupId);
    }

    @Override
    public void processLineUps(
            List<NewFamilyGroupLeaderLineUp> leaderLineUps,
            List<NewFamilyGroupMemberLineUp> memberLineUps
    ) {
        newFamilyGroupLineUpProcessor.process(leaderLineUps, memberLineUps);
    }

    @Override
    public boolean existsByLeaderUserIdAndTermId(Long leaderUserId, Long termId) {
        return newFamilyGroupFinder.existsByLeaderUserIdAndTermId(leaderUserId, termId);
    }

    @Override
    public boolean existsByLeaderUserIdInAndTermId(List<Long> leaderUserIds, Long termId) {
        return newFamilyGroupFinder.existsByLeaderUserIdInAndTermId(leaderUserIds, termId);
    }

    @Override
    public List<NewFamilyGroup> findByCodyId(Long codyId) {
        return newFamilyGroupFinder.findByCodyId(codyId);
    }

    @Override
    public List<NewFamilyGroup> findByTermId(Long termId) {
        return newFamilyGroupFinder.findByTermId(termId);
    }
}
