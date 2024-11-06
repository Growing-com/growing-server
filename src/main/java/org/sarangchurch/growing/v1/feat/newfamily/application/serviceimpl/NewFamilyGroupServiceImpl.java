package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupMemberValidator;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroup.NewFamilyGroupFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupServiceImpl implements NewFamilyGroupService {
    private final NewFamilyGroupFinder newFamilyGroupFinder;
    private final NewFamilyGroupMemberValidator newFamilyGroupMemberValidator;

    @Override
    public long countByCodyId(Long codyId) {
        return newFamilyGroupFinder.countByCodyId(codyId);
    }

    @Override
    public NewFamilyGroup findByIdOrThrow(Long newFamilyGroupId) {
        return newFamilyGroupFinder.findByIdOrThrow(newFamilyGroupId);
    }

    @Override
    public boolean areValidUserIdsByNewFamilyGroupId(List<Long> userIds, Long newFamilyGroupId) {
        return newFamilyGroupMemberValidator.areValidUserIdsByNewFamilyGroupId(userIds, newFamilyGroupId);
    }
}
