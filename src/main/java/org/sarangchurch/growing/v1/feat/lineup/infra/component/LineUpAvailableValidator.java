package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineUpAvailableValidator {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;

    public void validateAvailable(Term term, Long userId) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() -> {
                    StumpLineUp newStumpLineUp = StumpLineUp.builder()
                            .termId(term.getId())
                            .build();

                    return stumpLineUpWriter.save(newStumpLineUp);
                });

        // 1. 담당 교역자
        if (userId.equals(stumpLineUp.getSeniorPastorUserId())) {
            throw new IllegalStateException("이미 해당텀에 담당 교역자로 라인업된 유저입니다.");
        }

        // 2. 부 교역자

        // 3. 코디

        // 4. 일반 순장

        // 5. 새가족 순장

        // 6. 일반 순원

        // 7. 새가족 순원
    }
}
