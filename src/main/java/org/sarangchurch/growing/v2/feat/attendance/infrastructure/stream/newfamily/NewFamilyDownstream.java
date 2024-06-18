package org.sarangchurch.growing.v2.feat.attendance.infrastructure.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.newfamily.NewFamilyService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyDownstream {
    private final NewFamilyService newFamilyService;

    public boolean existsByIds(List<Long> ids) {
        return newFamilyService.existsByIds(ids);
    }
}
