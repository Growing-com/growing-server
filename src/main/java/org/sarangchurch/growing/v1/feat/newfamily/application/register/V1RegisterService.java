package org.sarangchurch.growing.v1.feat.newfamily.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyAppender;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1RegisterService {
    private final NewFamilyGroupValidator newFamilyGroupValidator;
    private final NewFamilyAppender newFamilyAppender;

    public void register(V1RegisterRequest request) {
        if (request.getNewFamilyGroupId() != null) {
            newFamilyGroupValidator.validateAvailable(request.getNewFamilyGroupId());
        }

        newFamilyAppender.append(request);
    }
}
