package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserFinder;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserWriter;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDispatcher {
    private final UserFinder userFinder;
    private final DispatchedUserFinder dispatchedUserFinder;
    private final DispatchedUserWriter dispatchedUserWriter;

    public void dispatch(List<DispatchedUser> dispatchedUsers) {
        List<Long> userIds = dispatchedUsers.stream()
                .map(DispatchedUser::getUserId)
                .collect(Collectors.toList());

        List<User> users = userFinder.findByIdInOrThrow(userIds);

        boolean containsInActive = users.stream()
                .anyMatch(it -> !it.isActive());

        if (containsInActive) {
            throw new IllegalStateException("비활성 유저가 포함되어 있습니다.");
        }

        boolean includesDispatched = dispatchedUserFinder.existsByUserIdIn(userIds);

        if (includesDispatched) {
            throw new IllegalStateException("이미 파송된 유저가 포함되어 있습니다.");
        }

        dispatchedUserWriter.saveAll(dispatchedUsers);
    }
}
