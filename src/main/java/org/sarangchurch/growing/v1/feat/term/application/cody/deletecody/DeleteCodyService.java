package org.sarangchurch.growing.v1.feat.term.application.cody.deletecody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyRemover;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCodyService {
    private final CodyRemover codyRemover;

    public void delete(Long codyId) {
        codyRemover.remove(codyId);
    }
}
