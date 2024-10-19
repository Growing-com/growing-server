package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.LineOutNewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.LineOutNewFamilyWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyLineInManager {
    private final LineOutNewFamilyFinder lineOutNewFamilyFinder;
    private final NewFamilyWriter newFamilyWriter;
    private final LineOutNewFamilyWriter lineOutNewFamilyWriter;

    @Transactional
    public NewFamily lineIn(Long lineOutNewFamilyId) {
        LineOutNewFamily lineoutNewFamily = lineOutNewFamilyFinder.findByIdOrThrow(lineOutNewFamilyId);

        NewFamily newFamily = NewFamily.from(lineoutNewFamily);

        newFamilyWriter.save(newFamily);
        lineOutNewFamilyWriter.deleteById(lineOutNewFamilyId);

        return newFamily;
    }
}
