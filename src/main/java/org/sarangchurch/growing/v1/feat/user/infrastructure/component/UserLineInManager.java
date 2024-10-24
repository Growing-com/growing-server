package org.sarangchurch.growing.v1.feat.user.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.lineoutuser.LineOutUser;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.LineOutUserReader;
import org.sarangchurch.growing.v1.feat.user.infrastructure.data.LineOutUserWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserLineInManager {
    private final LineOutUserReader lineOutUserReader;
    private final LineOutUserWriter lineOutUserWriter;

    @Transactional
    public LineOutUser lineIn(Long lineOutUserId) {
        LineOutUser lineOutUser = lineOutUserReader.findByIdOrThrow(lineOutUserId);

        lineOutUserWriter.deleteById(lineOutUser.getId());

        return lineOutUser;
    }
}
