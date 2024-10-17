package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.dispatch.DispatchRequest;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUserRepository;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDispatcher {
    private final UserFinder userFinder;
    private final DispatchedUserRepository dispatchedUserRepository;

    @Transactional
    public void dispatch(DispatchRequest request) {
        List<Long> userIds = request.getContent().stream()
                .map(DispatchRequest.DispatchRequestItem::getUserId)
                .collect(Collectors.toList());

        List<User> users = userFinder.findByIdIn(userIds);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        boolean includesInActive = users.stream()
                .anyMatch(it -> !it.isActive());

        if (includesInActive) {
            throw new IllegalStateException("비활성 유저가 포함되어 있습니다.");
        }

        boolean includesDispatched = dispatchedUserRepository.existsByUserIdIn(userIds);

        if (includesDispatched) {
            throw new IllegalStateException("이미 파송된 유저가 포함되어 있습니다.");
        }

        List<DispatchedUser> dispatchedUsers = request.getContent()
                .stream()
                .map(it -> DispatchedUser.builder()
                        .userId(it.getUserId())
                        .type(it.getType())
                        .sendDate(it.getSendDate())
                        .returnDate(it.getReturnDate())
                        .build())
                .collect(Collectors.toList());

        dispatchedUserRepository.saveAll(dispatchedUsers);
    }
}
