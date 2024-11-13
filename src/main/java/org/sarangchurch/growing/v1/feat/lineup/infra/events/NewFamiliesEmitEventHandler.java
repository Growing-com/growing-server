package org.sarangchurch.growing.v1.feat.lineup.infra.events;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.NewFamilyEmitter;
import org.sarangchurch.growing.v1.feat.newfamily.domain.NewFamiliesEmitEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamiliesEmitEventHandler {
    private final NewFamilyEmitter newFamilyEmitter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void on(NewFamiliesEmitEvent event) {
        List<Long> newFamilyIds = event.getNewFamilyIds();

        newFamilyEmitter.removeByNewFamilyIds(newFamilyIds);
    }
}
