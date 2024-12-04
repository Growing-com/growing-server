package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupService;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup.SmallGroupValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmallGroupServiceImpl implements SmallGroupService {
    private final SmallGroupValidator smallGroupValidator;
    private final SmallGroupFinder smallGroupFinder;

    @Override
    public void validateAvailable(List<Long> smallGroupIds) {
        smallGroupValidator.validateAvailable(smallGroupIds);
    }

    @Override
    public List<SmallGroup> findByCodyId(Long codyId) {
        return smallGroupFinder.findByCodyId(codyId);
    }

}
