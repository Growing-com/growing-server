package org.sarangchurch.growing.v1.feat.user.infra.data.graduateuser;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduateUserRepository;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GraduateUserWriter {
    private final GraduateUserRepository graduateUserRepository;

    public List<GraduatedUser> saveAll(List<GraduatedUser> graduatedUsers) {
        return graduateUserRepository.saveAll(graduatedUsers);
    }
}
