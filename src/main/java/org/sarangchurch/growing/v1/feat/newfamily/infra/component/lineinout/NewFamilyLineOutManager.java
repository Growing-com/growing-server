package org.sarangchurch.growing.v1.feat.newfamily.infra.component.lineinout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Events;
import org.sarangchurch.growing.v1.feat.newfamily.domain.NewFamiliesEmitEvent;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.lineoutnewfamily.LineOutNewFamilyWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyLineOutManager {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogManager newFamilyPromoteLogManager;

    private final NewFamilyWriter newFamilyWriter;
    private final LineOutNewFamilyWriter lineOutNewFamilyWriter;

    @Transactional
    public List<LineOutNewFamily> lineOut(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        // 새가족 라인아웃은 등반 이전에만 가능함
        newFamilyPromoteLogManager.validateBeforePromotedByNewFamilyIds(newFamilyIds);

        newFamilyWriter.deleteByIdIn(newFamilyIds);

        List<LineOutNewFamily> lineOutNewFamilies = lineOutNewFamilyWriter.saveAll(
                newFamilies.stream()
                        .map(LineOutNewFamily::from)
                        .collect(Collectors.toList())
        );

        Events.raise(new NewFamiliesEmitEvent(newFamilyIds));

        return lineOutNewFamilies;
    }
}
