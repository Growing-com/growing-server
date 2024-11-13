package org.sarangchurch.growing.v1.feat.lineup.infra.events;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.newfamilylineup.NewFamilyLineUpWriter;
import org.sarangchurch.growing.v1.feat.lineup.infra.stream.term.TermDownstream;
import org.sarangchurch.growing.v1.feat.newfamily.domain.NewFamiliesEmitEvent;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NewFamiliesEmitEventHandler {
    private final TermDownstream termDownstream;
    private final NewFamilyLineUpWriter newFamilyLineUpWriter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void on(NewFamiliesEmitEvent event) {
        Optional<Term> termOptional = termDownstream.findLineUpTerm();

        if (termOptional.isEmpty()) {
            return;
        }

        Term lineUpTerm = termOptional.get();
        newFamilyLineUpWriter.deleteByTermIdAndNewFamilyIdIn(lineUpTerm.getId(), event.getNewFamilyIds());
    }
}
