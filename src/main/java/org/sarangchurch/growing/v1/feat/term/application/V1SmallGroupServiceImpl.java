package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.V1SmallGroupService;
import org.sarangchurch.growing.v1.feat.term.infra.component.V1SmallGroupValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class V1SmallGroupServiceImpl implements V1SmallGroupService {
    private final V1SmallGroupValidator smallGroupValidator;

    @Override
    public void validateAvailable(List<Long> smallGroupIds) {
        smallGroupValidator.validateAvailable(smallGroupIds);
    }
}
