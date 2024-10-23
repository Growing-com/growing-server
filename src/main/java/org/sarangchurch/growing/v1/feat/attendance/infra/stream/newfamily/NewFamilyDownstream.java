package org.sarangchurch.growing.v1.feat.attendance.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyDownstream {
    private final NewFamilyService newFamilyService;

    public boolean existsAllByIds(List<Long> ids) {
        return newFamilyService.existsAllByIds(ids);
    }
}
