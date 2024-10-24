package org.sarangchurch.growing.v1.feat.term.infra.component.pastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.AssignValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.pastor.PastorWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PastorAppender {
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final PastorWriter pastorWriter;

    public void append(Long termId, Long pastorUserId) {
        Term term = termFinder.findActiveByIdOrThrow(termId);
        User user = userDownstream.findActiveByIdOrThrow(pastorUserId);

        assignValidator.validateAssignable(term, user);

        pastorWriter.save(
                Pastor.builder()
                        .termId(term.getId())
                        .userId(user.getId())
                        .isSenior(false)
                        .build()
        );
    }
}
