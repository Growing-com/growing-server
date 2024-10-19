package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyFinder;
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
    public boolean isNewFamilyByUserId(Long userId) {
        return newFamilyFinder.isNewFamilyByUserId(userId);
    }

    @Override
    public boolean containsNewFamilyByUserIds(List<Long> userIds) {
        return newFamilyFinder.containsNewFamilyByUserIds(userIds);
    }
}
