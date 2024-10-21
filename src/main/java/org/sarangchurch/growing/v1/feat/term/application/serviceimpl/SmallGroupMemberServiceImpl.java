package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.AssignValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupMemberAppender;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmallGroupMemberServiceImpl implements SmallGroupMemberService {
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final SmallGroupMemberAppender smallGroupMemberAppender;

    @Override
    public void create(Long userId, Long smallGroupId) {
        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(smallGroupId);
        Term term = termFinder.findActiveByIdOrThrow(smallGroup.getTermId());
        User user = userDownstream.findActiveByIdOrThrow(userId);

        assignValidator.validateAssignable(term, user);
        smallGroupMemberAppender.append(userId, smallGroupId);
    }
}
