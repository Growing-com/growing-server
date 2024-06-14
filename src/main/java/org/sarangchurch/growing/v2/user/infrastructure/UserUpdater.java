package org.sarangchurch.growing.v2.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.core.interfaces.common.Gender;
import org.sarangchurch.growing.v2.user.domain.User;
import org.sarangchurch.growing.v2.user.domain.UserEditor;
import org.sarangchurch.growing.v2.user.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserUpdater {
    private final UserRepository userRepository;

    @Transactional
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Gender gender, Integer grade) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지체입니다."));

        UserEditor userEditor = user.toEditor();

        userEditor.setName(name);
        userEditor.setPhoneNumber(phoneNumber);
        userEditor.setBirth(birth);
        userEditor.setGender(gender);
        userEditor.setGrade(grade);

        user.edit(userEditor);
    }
}
