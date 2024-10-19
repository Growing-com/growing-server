package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.GraduateUserReader;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.GraduateUserWriter;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserGraduateManager {
    private final UserFinder userFinder;
    private final GraduateUserReader graduateUserReader;
    private final GraduateUserWriter graduateUserWriter;

    @Transactional
    public void graduate(List<Long> userIds, LocalDate graduateDate) {
        List<User> users = userFinder.findByIdInOrThrow(userIds);

        boolean includesGraduated = graduateUserReader.existsByUserIdIn(userIds);

        if (includesGraduated) {
            throw new IllegalStateException("이미 졸업한 유저가 포함되어 있습니다.");
        }

        users.forEach(User::toInActive);

        List<GraduatedUser> graduatedUsers = userIds.stream()
                .map(it -> GraduatedUser.builder()
                        .userId(it)
                        .graduateDate(graduateDate)
                        .build())
                .collect(Collectors.toList());

        graduateUserWriter.saveAll(graduatedUsers);
    }
}
