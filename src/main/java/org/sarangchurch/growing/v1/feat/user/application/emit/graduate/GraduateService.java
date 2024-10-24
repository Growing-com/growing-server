package org.sarangchurch.growing.v1.feat.user.application.emit.graduate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.user.UserService;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.ActiveUserValidator;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserGraduateManager;
import org.sarangchurch.growing.v1.feat.user.infrastructure.stream.TermUpstream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraduateService {
    private final ActiveUserValidator activeUserValidator;
    private final TermUpstream termUpstream;
    private final UserGraduateManager graduateManager;
    private final UserService userService;

    @Transactional
    public void graduate(GraduateRequest request) {
        List<Long> userIds = request.getUserIds();
        activeUserValidator.validateByUserIds(userIds);
        termUpstream.emitByUserIds(userIds);

        List<GraduatedUser> graduatedUsers = request.toGraduatedUsers();
        graduateManager.graduate(graduatedUsers);
        userService.deActivateByIdIn(userIds);
    }
}
