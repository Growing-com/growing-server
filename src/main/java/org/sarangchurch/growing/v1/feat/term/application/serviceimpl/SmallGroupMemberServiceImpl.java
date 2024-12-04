package org.sarangchurch.growing.v1.feat.term.application.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupmember.SmallGroupMember;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;
import org.sarangchurch.growing.v1.feat.term.infra.component.AssignValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.LineUpEditor;
import org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup.SmallGroupMemberAppender;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroup.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.smallgroupmember.SmallGroupMemberFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.term.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.user.UserDownstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SmallGroupMemberServiceImpl implements SmallGroupMemberService {
    private final SmallGroupFinder smallGroupFinder;
    private final TermFinder termFinder;
    private final UserDownstream userDownstream;
    private final AssignValidator assignValidator;
    private final SmallGroupMemberAppender smallGroupMemberAppender;
    private final LineUpEditor lineUpEditor;
    private final SmallGroupMemberFinder smallGroupMemberFinder;

    @Override
    public void create(Long userId, Long smallGroupId) {
        SmallGroup smallGroup = smallGroupFinder.findByIdOrThrow(smallGroupId);
        Term term = termFinder.findActiveByIdOrThrow(smallGroup.getTermId());
        User user = userDownstream.findActiveByIdOrThrow(userId);

        assignValidator.validateAssignable(term, user);
        smallGroupMemberAppender.append(userId, smallGroupId);
    }

    @Override
    public void update(Long userId, Long targetSmallGroupId) {
        Long termId = smallGroupFinder.findByIdOrThrow(targetSmallGroupId).getTermId();
        Optional<SmallGroupMember> currentSmallGroupMemberOptional =
                smallGroupMemberFinder.findByUserIdAndTermId(userId, termId);

        if (currentSmallGroupMemberOptional.isPresent()) {
            SmallGroupMember currentSmallGroupMember = currentSmallGroupMemberOptional.get();
            lineUpEditor.edit(currentSmallGroupMember.getId(), targetSmallGroupId);

            return;
        }

        smallGroupMemberAppender.append(userId, targetSmallGroupId);
    }

    @Override
    public long countBySmallGroupIdIn(List<Long> smallGroupIds) {
        return smallGroupMemberFinder.countBySmallGroupIdIn(smallGroupIds);
    }
}
