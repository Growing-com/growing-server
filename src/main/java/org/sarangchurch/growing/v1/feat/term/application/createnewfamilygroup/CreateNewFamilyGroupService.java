package org.sarangchurch.growing.v1.feat.term.application.createnewfamilygroup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyValidator;
import org.sarangchurch.growing.v1.feat.term.infra.component.newfamilygroup.NewFamilyGroupAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewFamilyGroupService {
    private final CodyValidator codyValidator;
    private final NewFamilyGroupAppender newFamilyGroupAppender;

    public void create(Long termId, CreateNewFamilyGroupRequest request) {
        codyValidator.validateAvailableById(request.getCodyId());
        newFamilyGroupAppender.append(termId, request.getCodyId(), request.getLeaderUserId(), request.getMemberUserIds());
    }
}
