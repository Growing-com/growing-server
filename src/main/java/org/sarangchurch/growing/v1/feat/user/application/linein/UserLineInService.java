package org.sarangchurch.growing.v1.feat.user.application.linein;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserLineInManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLineInService {
    private final UserLineInManager userLineInManager;
    private final UserService userService;

    @Transactional
    public void lineIn(Long lineOutUserId) {
        LineOutUser lineOutUser = userLineInManager.lineIn(lineOutUserId);
        Long userId = lineOutUser.getUserId();
        userService.activateByIdIn(List.of(userId));
    }
}
