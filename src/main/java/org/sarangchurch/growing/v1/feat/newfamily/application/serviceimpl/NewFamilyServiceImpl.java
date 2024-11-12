package org.sarangchurch.growing.v1.feat.newfamily.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewFamilyServiceImpl implements NewFamilyService {
    private final NewFamilyFinder newFamilyFinder;

    @Override
    public boolean existsAllByIds(List<Long> ids) {
        return newFamilyFinder.existsAllByIds(ids);
    }

    @Override
    public boolean isNewFamilyByUserId(Long userId) {
        return newFamilyFinder.isNewFamilyByUserId(userId);
    }

    @Override
    public boolean containsNewFamilyByUserIds(List<Long> userIds) {
        return newFamilyFinder.containsNewFamilyByUserIds(userIds);
    }

    @Override
    public boolean existsByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyFinder.existsCurrentByNewFamilyGroupId(newFamilyGroupId);
    }

    @Override
    public List<NewFamily> findByIdInOrThrow(List<Long> newFamilyIds) {
        return newFamilyFinder.findByIdInOrThrow(newFamilyIds);
    }

    @Override
    public void processLineUps(List<NewFamilyLineUp> newFamilyLineUps) {
        log.info("Processing new family lineups");
    }
}
