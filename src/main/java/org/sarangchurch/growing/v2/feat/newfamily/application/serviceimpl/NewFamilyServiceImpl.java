package org.sarangchurch.growing.v2.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.newfamily.NewFamilyService;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewFamilyServiceImpl implements NewFamilyService {
    private final NewFamilyFinder newFamilyFinder;

    @Override
    public boolean existsByIds(List<Long> ids) {
        return newFamilyFinder.existsByIds(ids);
    }

    @Override
    public List<NewFamily> findByIdIn(List<Long> ids) {
        return newFamilyFinder.findByIdIn(ids);
    }
}
