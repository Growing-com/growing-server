package org.sarangchurch.growing.v1.feat.user.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserAppender;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserRegisterManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserAppender userAppender;
    private final UserRegisterManager userRegisterManager;

    public void register(UserRegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birth(request.getBirth())
                .sex(request.getSex())
                .grade(request.getGrade())
                .isActive(true)
                .build();

        if (request.getSmallGroupId() == null) {
            userAppender.append(user);

            return;
        }

        userRegisterManager.register(user, request.getSmallGroupId());
    }
}
