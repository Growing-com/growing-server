package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.register.RegisterRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyAppender {
    private final UserUpstream userUpstream;
    private final NewFamilyWriter newFamilyWriter;

    @Transactional
    public void append(RegisterRequest request) {
        User user = userUpstream.register(
                User.builder()
                        .name(request.getName())
                        .phoneNumber(request.getPhoneNumber())
                        .birth(request.getBirth())
                        .sex(request.getSex())
                        .grade(request.getGrade())
                        .isActive(true)
                        .build()
        );

        NewFamily newFamily = request.toEntity();

        newFamily.setUserId(user.getId());

        newFamilyWriter.save(newFamily);
    }
}
