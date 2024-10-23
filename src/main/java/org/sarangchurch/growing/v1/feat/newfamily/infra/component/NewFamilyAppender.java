package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyAppender {
    private final UserUpstream userUpstream;
    private final NewFamilyWriter newFamilyWriter;

    @Transactional
    public void append(User user, NewFamily newFamily) {
        User savedUser = userUpstream.register(user);

        newFamily.setUserId(savedUser.getId());

        newFamilyWriter.save(newFamily);
    }
}
