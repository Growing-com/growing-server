package org.sarangchurch.growing.v1.feat.user.application.linein;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserLineInManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLineInService {
    private final UserLineInManager userLineInManager;

    public void lineIn(Long lineOutUserId) {
        userLineInManager.lineIn(lineOutUserId);
    }
}
