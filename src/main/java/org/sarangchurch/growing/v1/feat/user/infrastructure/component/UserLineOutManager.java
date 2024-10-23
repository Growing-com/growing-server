package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.DispatchedUserWriter;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.LineOutUserReader;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.LineOutUserWriter;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.UserFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserLineOutManager {
    private final UserFinder userFinder;
    private final LineOutUserReader lineOutUserReader;
    private final LineOutUserWriter lineOutUserWriter;
    private final DispatchedUserWriter dispatchedUserWriter;

    @Transactional
    public void lineOut(List<LineOutUser> lineOutUsers) {
        List<Long> userIds = lineOutUsers.stream()
                .map(LineOutUser::getUserId)
                .collect(Collectors.toList());

        List<User> users = userFinder.findByIdInOrThrow(userIds);

        boolean containsInActiveUser = users.stream()
                .anyMatch(it -> !it.isActive());

        if (containsInActiveUser) {
            throw new IllegalStateException("비활성 유저가 포함되어 있습니다.");
        }

        boolean includesLineOut = lineOutUserReader.existsByUserIdIn(userIds);

        if (includesLineOut) {
            throw new IllegalStateException("이미 라인아웃된 유저가 포함되어 있습니다.");
        }

        lineOutUserWriter.saveAll(lineOutUsers);
        dispatchedUserWriter.deleteByUserIdIn(userIds);
    }
}
