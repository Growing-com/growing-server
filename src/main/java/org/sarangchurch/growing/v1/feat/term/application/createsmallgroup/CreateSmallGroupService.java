package org.sarangchurch.growing.v1.feat.term.application.createsmallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupAppender;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermFinder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSmallGroupService {
    private final TermFinder termFinder;
    private final CodyValidator codyValidator;
    private final SmallGroupAppender smallGroupAppender;

    public void create(Long termId, CreateSmallGroupRequest request) {
        termFinder.findActiveByIdOrThrow(termId);
        codyValidator.validateAvailableByIdAndTerm(request.getCodyId(), termId);
        smallGroupAppender.append(termId, request.getCodyId(), request.getLeaderUserId(), request.getMemberUserIds());
    }
}
