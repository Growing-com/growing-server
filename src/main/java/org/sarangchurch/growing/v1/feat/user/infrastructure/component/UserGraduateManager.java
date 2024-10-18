package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduateUserRepository;
import org.sarangchurch.growing.v1.feat.user.domain.graduateduser.GraduatedUser;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserGraduateManager {
    private final UserFinder userFinder;
    private final GraduateUserRepository graduateUserRepository;

    @Transactional
    public void graduate(List<Long> userIds, LocalDate graduateDate) {
        List<User> users = userFinder.findByIdIn(userIds);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        boolean includesGraduated = graduateUserRepository.existsByUserIdIn(userIds);

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

        graduateUserRepository.saveAll(graduatedUsers);
    }
}
