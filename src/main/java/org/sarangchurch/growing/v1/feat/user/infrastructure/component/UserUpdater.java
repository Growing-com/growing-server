package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.user.domain.User;
import org.sarangchurch.growing.v1.feat.user.domain.UserEditor;
import org.sarangchurch.growing.v1.feat.user.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserUpdater {
    private final UserRepository userRepository;

    @Transactional
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지체입니다."));

        UserEditor userEditor = user.toEditor();

        userEditor.setName(name);
        userEditor.setPhoneNumber(phoneNumber);
        userEditor.setBirth(birth);
        userEditor.setSex(sex);
        userEditor.setGrade(grade);

        user.edit(userEditor);
    }
}
