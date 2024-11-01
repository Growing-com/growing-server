package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.domain.user.UserEditor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUpdater {
    private final UserFinder userFinder;

    @Transactional
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade) {
        User user = userFinder.findByIdOrThrow(userId);

        UserEditor userEditor = user.toEditor();

        userEditor.setName(name);
        userEditor.setPhoneNumber(phoneNumber);
        userEditor.setBirth(birth);
        userEditor.setSex(sex);
        userEditor.setGrade(grade);

        user.edit(userEditor);
    }

    @Transactional
    public void update(Long userId, String name, String phoneNumber, LocalDate birth, Sex sex, Integer grade, String etc) {
        User user = userFinder.findByIdOrThrow(userId);

        UserEditor userEditor = user.toEditor();

        userEditor.setName(name);
        userEditor.setPhoneNumber(phoneNumber);
        userEditor.setBirth(birth);
        userEditor.setSex(sex);
        userEditor.setGrade(grade);
        userEditor.setEtc(etc);

        user.edit(userEditor);
    }

    @Transactional
    public void activate(List<User> users) {
        users.forEach(User::toActive);
    }

    @Transactional
    public void deactivate(List<User> users) {
        users.forEach(User::toInActive);
    }
}
