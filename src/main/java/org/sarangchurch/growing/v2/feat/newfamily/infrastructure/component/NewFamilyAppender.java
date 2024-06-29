package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.application.register.RegisterRequest;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.user.UserUpstream;
import org.sarangchurch.growing.v2.feat.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyAppender {
    private final NewFamilyRepository newFamilyRepository;
    private final UserUpstream userUpstream;

    @Transactional
    public void append(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birth(request.getBirth())
                .sex(request.getSex())
                .grade(request.getGrade())
                .build();

        User savedUser = userUpstream.register(user);

        newFamilyRepository.save(request.toEntity().setUserId(savedUser.getId()));
    }
}
