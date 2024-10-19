package org.sarangchurch.growing.v1.feat.user.infrastructure.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduateUserRepository;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GraduateUserWriter {
    private final GraduateUserRepository graduateUserRepository;

    public void saveAll(List<GraduatedUser> graduatedUsers) {
        graduateUserRepository.saveAll(graduatedUsers);
    }
}
