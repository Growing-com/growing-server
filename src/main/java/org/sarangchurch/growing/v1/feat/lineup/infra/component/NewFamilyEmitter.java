package org.sarangchurch.growing.v1.feat.lineup.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup.NewFamilyLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.term.TermDownstream;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewFamilyEmitter {
    private final TermDownstream termDownstream;
    private final NewFamilyLineUpWriter newFamilyLineUpWriter;

    public void removeByNewFamilyIds(List<Long> newFamilyIds) {
        Optional<Term> termOptional = termDownstream.findLineUpTerm();

        if (termOptional.isEmpty()) {
            return;
        }

        Term lineUpTerm = termOptional.get();

        newFamilyLineUpWriter.deleteByTermIdAndNewFamilyIdIn(lineUpTerm.getId(), newFamilyIds);
    }
}
