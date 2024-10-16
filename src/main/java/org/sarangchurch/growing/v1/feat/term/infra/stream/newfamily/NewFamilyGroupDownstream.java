package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupDownstream {
    private final NewFamilyGroupService newFamilyGroupService;

    public long countByCodyId(Long codyId) {
        return newFamilyGroupService.countByCodyId(codyId);
    }
}
