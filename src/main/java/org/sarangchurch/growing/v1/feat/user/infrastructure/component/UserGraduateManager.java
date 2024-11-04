package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserWriter;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.GraduateUserReader;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.GraduateUserWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserGraduateManager {
    private final GraduateUserReader graduateUserReader;
    private final GraduateUserWriter graduateUserWriter;
    private final DispatchedUserWriter dispatchedUserWriter;

    @Transactional
    public void graduate(List<GraduatedUser> graduatedUsers) {
        List<Long> userIds = graduatedUsers.stream()
                .map(GraduatedUser::getUserId)
                .collect(Collectors.toList());

        boolean includesGraduated = graduateUserReader.existsByUserIdIn(userIds);

        if (includesGraduated) {
            throw new IllegalStateException("이미 졸업한 유저가 포함되어 있습니다.");
        }

        graduateUserWriter.saveAll(graduatedUsers);
        dispatchedUserWriter.deleteByUserIdIn(userIds);
    }
}
