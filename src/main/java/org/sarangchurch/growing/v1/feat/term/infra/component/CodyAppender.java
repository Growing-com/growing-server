package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.cody.CodyRepository;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CodyAppender {
    private final TermFinder termFinder;
    private final CodyRepository codyRepository;
    private final UserDownstream userDownstream;

    @Transactional
    public void append(Long termId, Long codyUserId) {
        Term term = termFinder.findById(termId);

        // TODO: add active term validation elsewhere
        if (!term.isActive()) {
            throw new IllegalStateException("텀이 활성화되어야 코디를 추가할 수 있습니다.");
        }

        boolean alreadyExists = codyRepository.existsByUserIdAndTermId(codyUserId, termId);

        if (alreadyExists) {
            throw new IllegalArgumentException("이미 존재하는 코디입니다.");
        }

        User user = userDownstream.findById(codyUserId);

        if (!user.isActive()) {
            throw new IllegalStateException("활성 유저가 아닙니다.");
        }

        codyRepository.save(Cody.builder()
                .termId(termId)
                .userId(codyUserId)
                .build());
    }
}
