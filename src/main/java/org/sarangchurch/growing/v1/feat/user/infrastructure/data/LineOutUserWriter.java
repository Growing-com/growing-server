package org.sarangchurch.growing.v1.feat.user.infrastructure.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LineOutUserWriter {
    private final LineOutUserRepository lineOutUserRepository;

    public void deleteById(Long id) {
        lineOutUserRepository.deleteById(id);
    }

    public void saveAll(List<LineOutUser> lineOutUsers) {
        lineOutUserRepository.saveAll(lineOutUsers);
    }
}
