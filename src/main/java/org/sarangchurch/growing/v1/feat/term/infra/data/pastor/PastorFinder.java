package org.sarangchurch.growing.v1.feat.term.infra.data.pastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.PastorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PastorFinder {
    private final PastorRepository pastorRepository;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return pastorRepository.existsByUserIdAndTermId(userId, termId);
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return pastorRepository.existsByUserIdInAndTermId(userIds, termId);
    }

    public Pastor findByIdOrThrow(Long pastorId) {
        return pastorRepository.findById(pastorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 교역자입니다."));
    }

    public Pastor findSeniorByTermIdOrThrow(Long termId) {
        return pastorRepository.findSeniorByTermId(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 교역자입니다."));
    }

    public Pastor findByUserIdAndTermId(Long userId, Long termId) {
        return pastorRepository.findByUserIdAndTermId(userId, termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 교역자입니다."));
    }
}
