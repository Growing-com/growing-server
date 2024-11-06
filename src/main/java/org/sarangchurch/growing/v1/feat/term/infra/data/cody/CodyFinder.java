package org.sarangchurch.growing.v1.feat.term.infra.data.cody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.cody.CodyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CodyFinder {
    private final CodyRepository codyRepository;

    public boolean existsByUserIdAndTermId(Long userId, Long termId) {
        return codyRepository.existsByUserIdAndTermId(userId, termId);
    }

    public Cody findByIdOrThrow(Long id) {
        return codyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다"));
    }

    public boolean existsByUserIdInAndTermId(List<Long> userIds, Long termId) {
        return codyRepository.existsByUserIdInAndTermId(userIds, termId);
    }

    public Cody findByUserIdAndTermId(Long userId, Long termId) {
        return codyRepository.findByUserIdAndTermId(userId, termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다"));
    }

    public List<Cody> findByTermId(Long termId) {
        return codyRepository.findByTermId(termId);
    }
}
