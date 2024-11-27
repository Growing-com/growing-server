package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.CodyService;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodyServiceImpl implements CodyService {
    private final CodyFinder codyFinder;

    @Override
    public List<Cody> findByTermId(Long termId) {
        return codyFinder.findByTermId(termId);
    }
}
