package org.sarangchurch.growing.v2.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.stream.user.UserUpstream;
import org.sarangchurch.growing.v2.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyAppender {
    private final NewFamilyRepository newFamilyRepository;
    private final UserUpstream userUpstream;

    @Transactional
    public void append(NewFamily newFamily) {
        User user = User.builder()
                .name(newFamily.getName())
                .phoneNumber(newFamily.getPhoneNumber())
                .birth(newFamily.getBirth())
                .gender(newFamily.getGender())
                .grade(newFamily.getGrade())
                .build();

        User savedUser = userUpstream.register(user);

        newFamily.setUserId(savedUser.getId());

        newFamilyRepository.save(newFamily);
    }
}
