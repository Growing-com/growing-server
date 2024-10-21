package org.sarangchurch.growing.v1.feat.user.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserAppender userAppender;

    public void register(UserRegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birth(request.getBirth())
                .sex(request.getSex())
                .grade(request.getGrade())
                .isActive(true)
                .build();

        userAppender.append(user);
    }
}
