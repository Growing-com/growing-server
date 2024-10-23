package org.sarangchurch.growing.v1.feat.term.application.switchseniorpastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.pastor.SeniorPastorSwitcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SwitchSeniorPastorService {
    private final SeniorPastorSwitcher seniorPastorSwitcher;

    public void run(Long termId, Long targetSeniorPastorId) {
        seniorPastorSwitcher.run(termId, targetSeniorPastorId);
    }
}
