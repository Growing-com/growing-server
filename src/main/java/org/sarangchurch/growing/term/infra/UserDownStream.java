package org.sarangchurch.growing.term.infra;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.user.User;
import org.sarangchurch.growing.user.application.upstream.UserUpstream;
import org.sarangchurch.growing.user.domain.UserEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("Term_UserDownStream")
@Transactional
@RequiredArgsConstructor
public class UserDownStream {
    private final UserUpstream userUpstream;

    public User findById(Long userId) {
        UserEntity origin = userUpstream.findById(userId);

        return new User(origin.getId(), origin.getIsActive());
    }

    public void update(User user) {
        UserEntity origin = userUpstream.findById(user.getId());

        Boolean isActive = user.getIsActive();

        origin.update(
                origin.getName(),
                origin.getSex(),
                origin.getPhoneNumber(),
                origin.getBirth(),
                origin.getGrade(),
                isActive,
                origin.getVisitDate(),
                origin.getEtc()
        );
    }

    public UserEntity save(UserEntity user) {
        return userUpstream.save(user);
    }
}
