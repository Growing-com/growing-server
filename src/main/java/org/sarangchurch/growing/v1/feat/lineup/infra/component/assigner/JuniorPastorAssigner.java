package org.sarangchurch.growing.v1.feat.lineup.infra.component.assigner;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.types.Duty;
import org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup.StumpLineUp;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpFinder;
import org.sarangchurch.growing.v1.feat.lineup.infra.data.stumplineup.StumpLineUpWriter;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JuniorPastorAssigner {
    private final StumpLineUpFinder stumpLineUpFinder;
    private final StumpLineUpWriter stumpLineUpWriter;
    private final NormalLineUpAssignValidator normalLineUpAssignValidator;

    @Transactional
    public void assign(Term term, List<User> users) {
        StumpLineUp stumpLineUp = stumpLineUpFinder.findByTermId(term.getId())
                .orElseGet(() ->
                        stumpLineUpWriter.save(
                                StumpLineUp.builder()
                                        .termId(term.getId())
                                        .build()
                        )
                );

        users.forEach(user -> normalLineUpAssignValidator.validateDutyAssignable(term, user, Duty.JUNIOR_PASTOR));

        stumpLineUp.setJuniorPastors(users);
    }
}
