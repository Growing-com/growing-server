package org.sarangchurch.growing.v2.newfamily.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.LineoutNewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.LineoutNewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyLineoutManager {
    private final NewFamilyRepository newFamilyRepository;
    private final LineoutNewFamilyRepository lineoutNewFamilyRepository;

    @Transactional
    public void lineout(NewFamily newFamily) {
        LineoutNewFamily lineoutNewFamily = LineoutNewFamily.from(newFamily);

        newFamilyRepository.deleteById(newFamily.getId());

        lineoutNewFamilyRepository.save(lineoutNewFamily);
    }
}
