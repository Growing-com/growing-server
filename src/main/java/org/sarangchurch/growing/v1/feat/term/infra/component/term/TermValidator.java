package org.sarangchurch.growing.v1.feat.term.infra.component.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.domain.term.TermStatus;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.pastor.PastorFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TermValidator {
    private final TermFinder termFinder;
    private final CodyFinder codyFinder;
    private final PastorFinder pastorFinder;

    public boolean areValidStumpUserIds(List<Long> userIds, Long termId) {
        Term term = termFinder.findById(termId);

        List<Cody> codies = codyFinder.findByTermId(term.getId());
        List<Pastor> pastors = pastorFinder.findByTermId(term.getId());

        List<Long> stumpUserIds = new ArrayList<>();
        stumpUserIds.addAll(codies.stream().map(Cody::getUserId).collect(Collectors.toList()));
        stumpUserIds.addAll(pastors.stream().map(Pastor::getUserId).collect(Collectors.toList()));

        return new HashSet<>(stumpUserIds).containsAll(userIds);
    }

    public void validateLineUpNotPresent() {
        Optional<Term> lineUpTerm = termFinder.findByStatus(TermStatus.LINE_UP);

        if (lineUpTerm.isPresent()) {
            throw new IllegalStateException("이미 라인업을 진행중인 텀이 존재합니다.");
        }
    }
}
