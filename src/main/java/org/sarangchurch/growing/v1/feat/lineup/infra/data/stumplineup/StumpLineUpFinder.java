package org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUpRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StumpLineUpFinder {
    private final StumpLineUpRepository stumpLineUpRepository;

    public Optional<StumpLineUp> findByTermId(Long termId) {
        return stumpLineUpRepository.findByTermId(termId);
    }
}
