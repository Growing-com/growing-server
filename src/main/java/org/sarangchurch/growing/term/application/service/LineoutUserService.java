package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.user.User;
import org.sarangchurch.growing.term.infra.UserDownStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LineoutUserService {
    private final UserDownStream userDownStream;

    public void lineout(Long userId) {
        User user = userDownStream.findById(userId);
        user.lineout();
        userDownStream.update(user);
    }
}
