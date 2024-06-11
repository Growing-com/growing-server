package org.sarangchurch.growing.v2.newfamily.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.LineoutNewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.LineoutNewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LineoutService {
    private final NewFamilyRepository newFamilyRepository;
    private final LineoutNewFamilyRepository lineoutNewFamilyRepository;

    public void lineout(Long newFamilyId) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        LineoutNewFamily lineoutNewFamily = LineoutNewFamily.from(newFamily);

        newFamilyRepository.deleteById(newFamilyId);

        lineoutNewFamilyRepository.save(lineoutNewFamily);
    }
}
