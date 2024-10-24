package org.sarangchurch.growing.v1.feat.newfamily.infra.component.lineinout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.lineoutnewfamily.LineOutNewFamilyWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyWriter;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilypromotelog.NewFamilyPromoteLogWriter;
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
    private final NewFamilyPromoteLogWriter newFamilyPromoteLogWriter;

    @Transactional
    public List<LineOutNewFamily> lineOut(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        // 새가족 라인아웃은 등반 이전에만 가능함
        newFamilyPromoteLogManager.validateBeforePromotedByNewFamilyIds(newFamilyIds);

        // 라인아웃 + 등반 기록 삭제
        newFamilyWriter.deleteByIdIn(newFamilyIds);
        newFamilyPromoteLogWriter.deleteByIdIn(
                newFamilies.stream()
                        .map(NewFamily::getNewFamilyPromoteLogId)
                        .collect(Collectors.toList())
        );

        return lineOutNewFamilyWriter.saveAll(
                newFamilies.stream()
                        .map(LineOutNewFamily::from)
                        .collect(Collectors.toList())
        );
    }
}
