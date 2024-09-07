package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.newfamily.NewFamilyGroupLeaderService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupLeaderFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewFamilyGroupLeaderServiceImpl implements NewFamilyGroupLeaderService {
    private final NewFamilyGroupLeaderFinder newFamilyGroupLeaderFinder;

    @Override
    public NewFamilyGroupLeader findById(Long id) {
        return newFamilyGroupLeaderFinder.findById(id);
    }
}
