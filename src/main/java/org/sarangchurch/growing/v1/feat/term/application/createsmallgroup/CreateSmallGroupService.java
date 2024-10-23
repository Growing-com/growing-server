package org.sarangchurch.growing.v1.feat.term.application.createsmallgroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.smallgroup.SmallGroupAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSmallGroupService {
    private final CodyValidator codyValidator;
    private final SmallGroupAppender smallGroupAppender;

    public void create(Long termId, CreateSmallGroupRequest request) {
        codyValidator.validateAvailableById(request.getCodyId());
        smallGroupAppender.append(termId, request.getCodyId(), request.getLeaderUserId(), request.getMemberUserIds());
    }
}
