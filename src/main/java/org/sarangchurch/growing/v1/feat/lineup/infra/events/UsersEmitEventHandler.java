package org.sarangchurch.growing.v1.feat.lineup.infra.events;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.lineup.infra.component.UserEmitter;
import org.sarangchurch.growing.v1.feat.term.domain.UsersEmitEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersEmitEventHandler {
    private final UserEmitter userEmitter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void on(UsersEmitEvent event) {
        List<Long> userIds = event.getUserIds();

        userEmitter.removeByUserIds(userIds);
    }
}
