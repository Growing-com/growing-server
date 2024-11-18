package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("Term_NewFamily_Downstream")
@RequiredArgsConstructor
public class NewFamilyDownstream {
    private final NewFamilyService newFamilyService;

    public boolean existsByNewFamilyGroupId(Long newFamilyGroupId) {
        return newFamilyService.existsByNewFamilyGroupId(newFamilyGroupId);
    }

    public boolean containsNewFamilyByUserIds(List<Long> userIds) {
        return newFamilyService.containsNewFamilyByUserIds(userIds);
    }

    public boolean isNewFamilyByUserId(Long userId) {
        return newFamilyService.isNewFamilyByUserId(userId);
    }

    public boolean containsPromotedBySmallGroupId(Long smallGroupId) {
        return newFamilyService.containsPromotedBySmallGroupId(smallGroupId);
    }
}
