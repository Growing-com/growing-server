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

    public StumpLineUp findByTermIdOrThrow(Long termId) {
        return stumpLineUpRepository.findByTermId(termId)
                .orElseThrow(() -> new IllegalArgumentException("그루터기 라인업 기록이 존재하지 않습니다."));
    }

}
