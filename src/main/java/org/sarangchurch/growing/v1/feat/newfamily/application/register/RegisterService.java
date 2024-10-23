package org.sarangchurch.growing.v1.feat.newfamily.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyAppender;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyGroupValidator;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
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

        User user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birth(request.getBirth())
                .sex(request.getSex())
                .grade(request.getGrade())
                .isActive(true)
                .build();

        NewFamily newFamily = request.toEntity();

        newFamilyAppender.append(user, newFamily);
    }
}
