package org.sarangchurch.growing.v1.feat.user.infrastructure.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LineOutUserReader {
    private final LineOutUserRepository lineOutUserRepository;

    public LineOutUser findByIdOrThrow(Long id) {
        return lineOutUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이탈자입니다."));
    }

    public boolean existsByUserIdIn(List<Long> userIds) {
        return lineOutUserRepository.existsByUserIdIn(userIds);
    }
}
