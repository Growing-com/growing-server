package org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("LineUp_NewFamily_Downstream")
@RequiredArgsConstructor
public class NewFamilyDownstream {
    private final NewFamilyService newFamilyService;

    public boolean isNewFamilyByUserId(Long userId) {
        return newFamilyService.isNewFamilyByUserId(userId);
    }

    public List<NewFamily> findByIdInOrThrow(List<Long> newFamilyIds) {
        return newFamilyService.findByIdInOrThrow(newFamilyIds);
    }
}
