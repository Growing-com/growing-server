package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.application.lineout.UserLineOutRequest;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUserRepository;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserLineOutManager {
    private final UserFinder userFinder;
    private final LineOutUserRepository lineOutUserRepository;

    @Transactional
    public void lineOut(UserLineOutRequest request) {
        List<Long> userIds = request.getContent()
                .stream()
                .map(UserLineOutRequest.UserLineOutRequestItem::getUserId)
                .collect(Collectors.toList());

        List<User> users = userFinder.findByIdIn(userIds);

        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        boolean includesLineOut = lineOutUserRepository.existsByUserIdIn(userIds);

        if (includesLineOut) {
            throw new IllegalStateException("이미 졸업한 유저가 포함되어 있습니다.");
        }

        List<LineOutUser> lineOutUsers = request.getContent()
                .stream()
                .map(it -> LineOutUser.builder()
                        .userId(it.getUserId())
                        .lineOutDate(it.getLineOutDate())
                        .reason(it.getReason())
                        .build())
                .collect(Collectors.toList());

        lineOutUserRepository.saveAll(lineOutUsers);
    }
}
