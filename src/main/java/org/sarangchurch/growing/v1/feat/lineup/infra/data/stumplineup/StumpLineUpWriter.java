package org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUpRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StumpLineUpWriter {
    private final StumpLineUpRepository stumpLineUpRepository;

    public StumpLineUp save(StumpLineUp stumpLineUp) {
        return stumpLineUpRepository.save(stumpLineUp);
    }
}
