package org.sarangchurch.growing.v1.feat.newfamily.application.linein;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyLineInManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineInService {
    private final NewFamilyLineInManager lineInManager;

    public void lineIn(Long lineOutNewFamilyId) {
        lineInManager.lineIn(lineOutNewFamilyId);
    }
}
