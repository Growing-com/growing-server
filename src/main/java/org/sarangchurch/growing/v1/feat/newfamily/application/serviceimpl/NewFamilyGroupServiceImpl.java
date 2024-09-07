package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupServiceImpl implements NewFamilyGroupService {
    private final NewFamilyGroupFinder newFamilyGroupFinder;

    @Override
    public NewFamilyGroup findById(Long id) {
        return newFamilyGroupFinder.findById(id);
    }
}
