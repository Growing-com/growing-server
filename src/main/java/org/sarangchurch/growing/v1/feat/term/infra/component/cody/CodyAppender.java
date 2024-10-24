package org.sarangchurch.growing.v1.feat.term.infra.component.cody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.AssignValidator;
import org.sarangchurch.growing.v1.feat.term.infra.data.cody.CodyWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodyAppender {
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final CodyWriter codyWriter;

    public void append(Long termId, Long codyUserId) {
        Term term = termFinder.findActiveByIdOrThrow(termId);
        User user = userDownstream.findActiveByIdOrThrow(codyUserId);

        assignValidator.validateAssignable(term, user);

        codyWriter.save(
                Cody.builder()
                        .termId(term.getId())
                        .userId(user.getId())
                        .build()
        );
    }
}
