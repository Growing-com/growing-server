package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.LineOutUserReader;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.LineOutUserWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserLineInManager {
    private final LineOutUserReader lineOutUserReader;
    private final UserService userService;
    private final LineOutUserWriter lineOutUserWriter;

    @Transactional
    public void lineIn(Long lineOutUserId) {
        LineOutUser lineOutUser = lineOutUserReader.findByIdOrThrow(lineOutUserId);

        userService.activateByIdIn(Collections.singletonList(lineOutUser.getUserId()));

        lineOutUserWriter.deleteById(lineOutUser.getId());
    }
}
