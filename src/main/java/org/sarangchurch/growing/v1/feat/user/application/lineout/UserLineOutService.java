package org.sarangchurch.growing.v1.feat.user.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserLineOutManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserLineOutService {
    private final UserLineOutManager lineOutManager;
    private final UserService userService;

    @Transactional
    public void lineOut(UserLineOutRequest request) {
        List<Long> userIds = request.getContent()
                .stream()
                .map(UserLineOutRequest.UserLineOutRequestItem::getUserId)
                .collect(Collectors.toList());

        List<LineOutUser> lineOutUsers = request.getContent()
                .stream()
                .map(it -> LineOutUser.builder()
                        .userId(it.getUserId())
                        .lineOutDate(it.getLineOutDate())
                        .reason(it.getReason())
                        .build())
                .collect(Collectors.toList());

        lineOutManager.lineOut(lineOutUsers);

        userService.deActivateByIdIn(userIds);
    }
}
