package org.sarangchurch.growing.v2.newfamily.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.infrastructure.component.NewFamilyAppender;
import org.sarangchurch.growing.v2.newfamily.infrastructure.component.NewFamilyGroupValidator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final NewFamilyGroupValidator newFamilyGroupValidator;
    private final NewFamilyAppender newFamilyAppender;

    public void register(RegisterRequest request) {
        if (request.getNewFamilyGroupId() != null) {
            newFamilyGroupValidator.validateAvailable(request.getNewFamilyGroupId());
        }

        newFamilyAppender.append(request);
    }
}
