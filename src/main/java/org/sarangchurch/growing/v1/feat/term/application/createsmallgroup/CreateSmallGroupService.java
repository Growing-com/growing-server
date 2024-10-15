package org.sarangchurch.growing.v1.feat.term.application.createsmallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupAppender;
import org.sarangchurch.growing.v1.feat.term.infra.component.TermValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSmallGroupService {
    private final TermValidator termValidator;
    private final CodyValidator codyValidator;
    private final SmallGroupAppender smallGroupAppender;

    public void create(Long termId, CreateSmallGroupRequest request) {
        termValidator.validateActive(termId);
        codyValidator.validateAvailableByIdAndTerm(request.getCodyId(), termId);
        smallGroupAppender.append(termId, request.getCodyId(), request.getLeaderUserId(), request.getMemberUserIds());
    }
}
