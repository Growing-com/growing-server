package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.CodyService;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("NewFamily_Cody_Downstream")
@RequiredArgsConstructor
public class CodyDownstream {
    private final CodyService codyService;

    public List<Cody> findByTermId(Long termId) {
        return codyService.findByTermId(termId);
    }

}
