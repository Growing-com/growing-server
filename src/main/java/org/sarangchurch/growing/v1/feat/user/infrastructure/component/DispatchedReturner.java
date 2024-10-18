package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DispatchedReturner {
    private final DispatchedUserRepository dispatchedUserRepository;

    @Transactional
    public void returnDispatched(Long dispatchUserId) {
        DispatchedUser dispatchedUser = dispatchedUserRepository.findById(dispatchUserId)
                .orElseThrow(() -> new IllegalArgumentException("파송자가 아닙니다."));

        dispatchedUserRepository.deleteById(dispatchedUser.getId());
    }
}
