package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.user.UserDownstream;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v2.feat.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyRollbackManager {
    private final LineoutNewFamilyRepository lineoutNewFamilyRepository;
    private final NewFamilyRepository newFamilyRepository;
    private final UserDownstream userDownstream;

    @Transactional
    public NewFamily rollback(Long lineOutNewFamilyId) {
        LineoutNewFamily lineoutNewFamily = lineoutNewFamilyRepository.findById(lineOutNewFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이탈자입니다."));

        User user = userDownstream.findById(lineoutNewFamily.getUserId());

        NewFamily newFamily = NewFamily.builder()
                .visitDate(lineoutNewFamily.getVisitDate())
                .etc(lineoutNewFamily.getEtc())
                .build();

        newFamily.setUserId(user.getId());

        lineoutNewFamilyRepository.deleteById(lineOutNewFamilyId);

        return newFamilyRepository.save(newFamily);
    }
}
