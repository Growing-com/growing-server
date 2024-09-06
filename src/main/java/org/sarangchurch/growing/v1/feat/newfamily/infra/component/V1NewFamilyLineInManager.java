package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class V1NewFamilyLineInManager {
    private final NewFamilyRepository newFamilyRepository;
    private final LineoutNewFamilyRepository lineoutNewFamilyRepository;

    public void lineIn(Long lineOutNewFamilyId) {
        LineoutNewFamily lineoutNewFamily = lineoutNewFamilyRepository.findById(lineOutNewFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이탈자입니다."));

        newFamilyRepository.save(NewFamily.from(lineoutNewFamily));
        lineoutNewFamilyRepository.deleteById(lineOutNewFamilyId);
    }
}
