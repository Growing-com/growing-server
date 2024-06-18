package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.newfamily.NewFamilyGroupService;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyGroup;
import org.sarangchurch.growing.v2.newfamily.infrastructure.component.NewFamilyGroupFinder;
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
