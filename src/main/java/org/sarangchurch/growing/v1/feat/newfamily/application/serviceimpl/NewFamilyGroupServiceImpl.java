package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyGroupFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupServiceImpl implements NewFamilyGroupService {
    private final NewFamilyGroupFinder newFamilyGroupFinder;

    @Override
    public long countByCodyId(Long codyId) {
        return newFamilyGroupFinder.countByCodyId(codyId);
    }

    @Override
    public NewFamilyGroup findByIdOrThrow(Long newFamilyGroupId) {
        return newFamilyGroupFinder.findByIdOrThrow(newFamilyGroupId);
    }
}
