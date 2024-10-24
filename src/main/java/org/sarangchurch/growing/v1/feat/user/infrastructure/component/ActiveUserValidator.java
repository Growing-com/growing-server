package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActiveUserValidator {
    private final UserFinder userFinder;

    public void validateByUserIds(List<Long> userIds) {
        List<User> users = userFinder.findByIdIn(userIds);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("올바르지 않은 유저 id가 포함되어 있습니다.");
        }

        boolean containsInActive = users.stream()
                .anyMatch(it -> !it.isActive());

        if (containsInActive) {
            throw new IllegalStateException("비활성 유저가 포함되어 있습니다.");
        }
    }
}
