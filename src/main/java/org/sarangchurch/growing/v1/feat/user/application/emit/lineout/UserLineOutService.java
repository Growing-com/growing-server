package org.sarangchurch.growing.v1.feat.user.application.emit.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.infra.component.ActiveUserValidator;
import org.sarangchurch.growing.v1.feat.user.infra.component.emit.UserLineOutManager;
import org.sarangchurch.growing.v1.feat.user.infra.stream.TermUpstream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLineOutService {
    private final ActiveUserValidator activeUserValidator;
    private final TermUpstream termUpstream;
    private final UserLineOutManager userLineOutManager;
    private final UserService userService;

    @Transactional
    public void lineOut(UserLineOutRequest request) {
        List<Long> userIds = request.getUserIds();
        activeUserValidator.validateByUserIds(userIds);
        termUpstream.emitByUserIds(userIds);

        List<LineOutUser> lineOutUsers = request.toLineOutUsers();
        userLineOutManager.lineOut(lineOutUsers);
        userService.deActivateByIdIn(userIds);
    }
}
