package org.sarangchurch.growing.v1.feat.user.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
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
        lineOutManager.lineOut(request);

        List<Long> userIds = request.getContent()
                .stream()
                .map(UserLineOutRequest.UserLineOutRequestItem::getUserId)
                .collect(Collectors.toList());

        userService.deActivateByIdIn(userIds);
    }
}
