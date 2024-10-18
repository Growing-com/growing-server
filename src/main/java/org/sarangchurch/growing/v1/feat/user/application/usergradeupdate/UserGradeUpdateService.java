package org.sarangchurch.growing.v1.feat.user.application.usergradeupdate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserGradeUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGradeUpdateService {
    private final UserGradeUpdater userGradeUpdater;

    public void update(UserGradeUpdateRequest request) {
        userGradeUpdater.update(request.getOption());
    }
}
