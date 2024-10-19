package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.dispatch.DispatchRequest;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserFinder;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserWriter;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDispatcher {
    private final UserFinder userFinder;
    private final DispatchedUserFinder dispatchedUserFinder;
    private final DispatchedUserWriter dispatchedUserWriter;

    @Transactional
    public void dispatch(DispatchRequest request) {
        List<Long> userIds = request.getContent().stream()
                .map(DispatchRequest.DispatchRequestItem::getUserId)
                .collect(Collectors.toList());

        userFinder.findByIdInOrThrow(userIds);

        boolean includesDispatched = dispatchedUserFinder.existsByUserIdIn(userIds);

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

        dispatchedUserWriter.saveAll(dispatchedUsers);
    }
}
