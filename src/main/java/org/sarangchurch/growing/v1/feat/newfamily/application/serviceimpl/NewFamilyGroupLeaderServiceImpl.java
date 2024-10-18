package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupLeaderService;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupLeaderFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupLeaderServiceImpl implements NewFamilyGroupLeaderService {
    private final NewFamilyGroupLeaderFinder newFamilyGroupLeaderFinder;

    @Override
    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupLeaderFinder.existsByUserIdAndTermId(userId, termId);
    }
}
