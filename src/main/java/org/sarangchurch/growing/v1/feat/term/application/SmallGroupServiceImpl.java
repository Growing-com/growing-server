package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.SmallGroupService;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmallGroupServiceImpl implements SmallGroupService {
    private final SmallGroupValidator smallGroupValidator;

    @Override
    public void validateAvailable(List<Long> smallGroupIds) {
        smallGroupValidator.validateAvailable(smallGroupIds);
    }
}
