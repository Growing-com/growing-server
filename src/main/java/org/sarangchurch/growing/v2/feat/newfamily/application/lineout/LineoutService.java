package org.sarangchurch.growing.v2.feat.newfamily.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyFinder;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component.NewFamilyLineoutManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineoutService {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyLineoutManager newFamilyLineoutManager;

    public void lineout(Long newFamilyId) {
        NewFamily newFamily = newFamilyFinder.findById(newFamilyId);

        newFamilyLineoutManager.lineout(newFamily);
    }
}
