package org.sarangchurch.growing.v1.feat.term.application.deletepastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.pastor.PastorRemover;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePastorService {
    private final PastorRemover pastorRemover;

    public void delete(Long pastorId) {
        pastorRemover.remove(pastorId);
    }
}
