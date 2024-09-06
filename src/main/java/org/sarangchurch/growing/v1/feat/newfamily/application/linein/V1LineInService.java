package org.sarangchurch.growing.v1.feat.newfamily.application.linein;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1NewFamilyLineInManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1LineInService {
    private final V1NewFamilyLineInManager lineInManager;

    public void lineIn(Long lineOutNewFamilyId) {
        lineInManager.lineIn(lineOutNewFamilyId);
    }
}
