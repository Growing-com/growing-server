package org.sarangchurch.growing.v2.newfamily.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyFinder;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyLineoutManager;
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
