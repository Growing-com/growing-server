package org.sarangchurch.growing.v1.feat.user.infra.data.graduateuser;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduateUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GraduateUserReader {
    private final GraduateUserRepository graduateUserRepository;

    public boolean existsByUserIdIn(List<Long> userIds) {
        return graduateUserRepository.existsByUserIdIn(userIds);
    }
}
