package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpRequester {
    private final NewFamilyFinder newFamilyFinder;

    @Transactional
    public void requestLineUp(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        newFamilies.forEach(NewFamily::toLineUpRequest);
    }
}
