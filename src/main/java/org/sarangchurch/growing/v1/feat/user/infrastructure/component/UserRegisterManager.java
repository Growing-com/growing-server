package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.sarangchurch.growing.v1.feat.user.infrastructure.stream.SmallGroupMemberUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserRegisterManager {
    private final UserAppender userAppender;
    private final SmallGroupMemberUpstream smallGroupMemberUpstream;

    @Transactional
    public void register(User user, Long smallGroupId) {
        User savedUser = userAppender.append(user);
        smallGroupMemberUpstream.create(savedUser.getId(), smallGroupId);
    }
}
