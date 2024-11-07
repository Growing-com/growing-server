package org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.springframework.stereotype.Component;

@Component("LineUp_NewFamily_Downstream")
@RequiredArgsConstructor
public class NewFamilyDownstream {
    private final NewFamilyService newFamilyService;

    public boolean isNewFamilyByUserId(Long userId) {
        return newFamilyService.isNewFamilyByUserId(userId);
    }
}
