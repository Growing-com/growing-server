package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.usergradeupdate.UserGradeUpdateOption;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserGradeUpdater {
    private final UserFinder userFinder;

    @Transactional
    public void update(UserGradeUpdateOption option) {
        List<User> users = userFinder.findAll();

        if (option == UserGradeUpdateOption.UP) {
            users.forEach(User::increaseGrade);

            return;
        }

        users.forEach(User::decreaseGrade);
    }
}
