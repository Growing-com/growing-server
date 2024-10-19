package org.sarangchurch.growing.v1.feat.attendance.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyDownstream {
    private final NewFamilyService newFamilyService;

    public boolean existsByIds(List<Long> ids) {
        return newFamilyService.existsByIds(ids);
    }

    public boolean containsNewFamilyByUserIds(List<Long> userIds) {
        return newFamilyService.containsNewFamilyByUserIds(userIds);
    }

    public boolean isNewFamilyByUserId(Long userId) {
        return newFamilyService.isNewFamilyByUserId(userId);
    }
}
