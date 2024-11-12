package org.sarangchurch.growing.v1.feat.lineup.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyService;
import org.sarangchurch.growing.v1.feat.lineup.domain.newfamilylineup.NewFamilyLineUp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("LineUp_NewFamily_Upstream")
@RequiredArgsConstructor
public class NewFamilyUpstream {
    private final NewFamilyService newFamilyService;

    public void processLineUps(List<NewFamilyLineUp> newFamilyLineUps) {
        newFamilyService.processLineUps(newFamilyLineUps);
    }
}
