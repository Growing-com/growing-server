package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserLineInManager {
    private final LineOutUserRepository lineOutUserRepository;
    private final UserService userService;

    @Transactional
    public void lineIn(Long lineOutUserId) {
        LineOutUser lineOutUser = lineOutUserRepository.findById(lineOutUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이탈자입니다."));

        userService.activateByIdIn(Collections.singletonList(lineOutUser.getUserId()));

        lineOutUserRepository.deleteById(lineOutUser.getId());
    }
}
