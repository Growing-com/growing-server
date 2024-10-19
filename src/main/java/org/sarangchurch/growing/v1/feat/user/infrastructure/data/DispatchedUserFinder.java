package org.sarangchurch.growing.v1.feat.user.infrastructure.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DispatchedUserFinder {
    private final DispatchedUserRepository dispatchedUserRepository;

    public DispatchedUser findByIdOrThrow(Long id) {
        return dispatchedUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("파송자가 아닙니다."));
    }

    public boolean existsByUserIdIn(List<Long> userIds) {
        return dispatchedUserRepository.existsByUserIdIn(userIds);
    }
}
