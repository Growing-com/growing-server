package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupMemberService;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupMemberFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupMemberServiceImpl implements NewFamilyGroupMemberService {
    private final NewFamilyGroupMemberFinder newFamilyGroupMemberFinder;

    @Override
    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return newFamilyGroupMemberFinder.existsByUserIdAndTermId(userId, termId);
    }

    @Override
    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return newFamilyGroupMemberFinder.existsByUserIdInAndTermId(userIds, termId);
    }
}
