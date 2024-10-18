package org.sarangchurch.growing.v1.feat.newfamily.application.linein;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyLineInManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LineInService {
    private final NewFamilyLineInManager lineInManager;
    private final UserUpstream userUpstream;

    @Transactional
    public void lineIn(Long lineOutNewFamilyId) {
        NewFamily newFamily = lineInManager.lineIn(lineOutNewFamilyId);

        userUpstream.activateByIdIn(Collections.singletonList(newFamily.getUserId()));
    }
}
