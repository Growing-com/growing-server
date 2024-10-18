package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyLineInManager {
    private final NewFamilyRepository newFamilyRepository;
    private final LineOutNewFamilyRepository lineoutNewFamilyRepository;

    @Transactional
    public NewFamily lineIn(Long lineOutNewFamilyId) {
        LineOutNewFamily lineoutNewFamily = lineoutNewFamilyRepository.findById(lineOutNewFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이탈자입니다."));

        NewFamily newFamily = NewFamily.from(lineoutNewFamily);

        newFamilyRepository.save(newFamily);
        lineoutNewFamilyRepository.deleteById(lineOutNewFamilyId);

        return newFamily;
    }
}
