package org.sarangchurch.growing.v1.feat.user.application.dispatch;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserDispatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DispatchService {
    private final UserDispatcher userDispatcher;

    public void dispatch(DispatchRequest request) {
        List<DispatchedUser> dispatchedUsers = request.getContent()
                .stream()
                .map(it -> DispatchedUser.builder()
                        .userId(it.getUserId())
                        .type(it.getType())
                        .sendDate(it.getSendDate())
                        .returnDate(it.getReturnDate())
                        .build())
                .collect(Collectors.toList());

        userDispatcher.dispatch(dispatchedUsers);
    }
}
