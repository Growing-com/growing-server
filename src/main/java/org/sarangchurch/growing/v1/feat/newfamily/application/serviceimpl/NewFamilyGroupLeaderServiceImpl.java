package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupLeaderService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupleader.NewFamilyGroupLeaderFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupleader.NewFamilyGroupLeaderWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupLeaderServiceImpl implements NewFamilyGroupLeaderService {
    private final NewFamilyGroupLeaderFinder newFamilyGroupLeaderFinder;
    private final NewFamilyGroupLeaderWriter newFamilyGroupLeaderWriter;

    @Override
    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderFinder.existsByUserIdAndTermId(userId, termId);
    }

    @Override
    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupLeaderFinder.existsByUserIdInAndTermId(userIds, termId);
    }

    @Override
    public NewFamilyGroupLeader save(NewFamilyGroupLeader newFamilyGroupLeader) {
        return newFamilyGroupLeaderWriter.save(newFamilyGroupLeader);
    }

    @Override
    public void deleteById(Long newFamilyGroupLeaderId) {
        newFamilyGroupLeaderWriter.deleteById(newFamilyGroupLeaderId);
    }
}
