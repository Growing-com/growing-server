package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.SmallGroupService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SmallGroupDownstream {
    private final SmallGroupService smallGroupService;

    public void validateAvailable(List<Long> smallGroupIds) {
        smallGroupService.validateAvailable(smallGroupIds);
    }
}
