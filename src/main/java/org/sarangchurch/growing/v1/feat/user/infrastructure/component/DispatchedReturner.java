package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserFinder;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DispatchedReturner {
    private final DispatchedUserFinder dispatchedUserFinder;
    private final DispatchedUserWriter dispatchedUserWriter;

    public void returnDispatched(Long dispatchUserId) {
        DispatchedUser dispatchedUser = dispatchedUserFinder.findByIdOrThrow(dispatchUserId);

        dispatchedUserWriter.deleteById(dispatchedUser.getId());
    }
}
