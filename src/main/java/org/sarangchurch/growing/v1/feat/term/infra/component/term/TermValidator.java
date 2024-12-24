package org.sarangchurch.growing.v1.feat.term.infra.component.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermStatus;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TermValidator {
    private final TermFinder termFinder;
    private final CodyFinder codyFinder;

    public boolean areValidCodyUserIdsByTermId(List<Long> userIds, Long termId) {
        Term term = termFinder.findById(termId);

        List<Long> codyUserIds = codyFinder.findByTermId(term.getId()).stream()
                .map(Cody::getUserId)
                .collect(Collectors.toList());

        return new HashSet<>(codyUserIds).containsAll(userIds);
    }

    public void validateLineUpNotPresent() {
        Optional<Term> lineUpTerm = termFinder.findByStatus(TermStatus.LINE_UP);

        if (lineUpTerm.isPresent()) {
            throw new IllegalStateException("이미 라인업을 진행중인 텀이 존재합니다.");
        }
    }
}
