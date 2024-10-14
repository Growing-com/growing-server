package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.cody.CodyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodyValidator {
    private final CodyRepository codyRepository;

    public void validateAvailableByIdAndTerm(Long codyId, Long termId) {
        Cody cody = codyRepository.findById(codyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다"));

        boolean isTermIdMatch = cody.getTermId().equals(termId);

        if (!isTermIdMatch) {
            throw new IllegalArgumentException("올바르지 않은 텀 id 값입니다.");
        }
    }
}
