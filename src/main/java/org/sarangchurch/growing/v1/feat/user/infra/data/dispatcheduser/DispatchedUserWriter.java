package org.sarangchurch.growing.v1.feat.user.infra.data.dispatcheduser;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUser;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchedUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DispatchedUserWriter {
    private final DispatchedUserRepository dispatchedUserRepository;

    public void deleteById(Long id) {
        dispatchedUserRepository.deleteById(id);
    }

    public void saveAll(List<DispatchedUser> dispatchedUsers) {
        dispatchedUserRepository.saveAll(dispatchedUsers);
    }

    public void deleteByUserIdIn(List<Long> userIds) {
        dispatchedUserRepository.deleteByUserIdIn(userIds);
    }
}
