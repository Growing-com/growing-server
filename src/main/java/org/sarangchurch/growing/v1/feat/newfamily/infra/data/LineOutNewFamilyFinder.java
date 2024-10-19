package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamilyRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineOutNewFamilyFinder {
    private final LineOutNewFamilyRepository lineOutNewFamilyRepository;

    public LineOutNewFamily findByIdOrThrow(Long id) {
        return lineOutNewFamilyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이탈자입니다."));
    }
}
