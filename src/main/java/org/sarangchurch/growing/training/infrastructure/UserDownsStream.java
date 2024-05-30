package org.sarangchurch.growing.training.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.user.application.upstream.UserUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("Training_UserDownStream")
@Transactional
@RequiredArgsConstructor
public class UserDownsStream {
    private final UserUpstream userUpstream;

    public void validateIds(List<Long> userIds) {
        userUpstream.validateIds(userIds);
    }
}
