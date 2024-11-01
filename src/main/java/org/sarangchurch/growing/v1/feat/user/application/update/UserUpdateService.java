package org.sarangchurch.growing.v1.feat.user.application.update;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.infrastructure.component.UserUpdater;
import org.sarangchurch.growing.v1.feat.user.infrastructure.stream.SmallGroupMemberUpstream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpdateService {
    private final UserUpdater userUpdater;
    private final SmallGroupMemberUpstream smallGroupMemberUpstream;

    @Transactional
    public void update(Long userId, UserUpdateRequest request) {
        if (request.getSmallGroupId() != null) {
            smallGroupMemberUpstream.update(userId, request.getSmallGroupId());
        }

        userUpdater.update(
                userId,
                request.getName(),
                request.getPhoneNumber(),
                request.getBirth(),
                request.getSex(),
                request.getGrade(),
                request.getEtc()
        );
    }
}
