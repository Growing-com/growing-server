package org.sarangchurch.growing.v1.feat.newfamily.infra.component.lineinout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyPromoteLogLookUpManager;
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
    private final NewFamilyPromoteLogLookUpManager newFamilyPromoteLogLookUpManager;

    private final NewFamilyWriter newFamilyWriter;
    private final LineOutNewFamilyWriter lineOutNewFamilyWriter;
    private final NewFamilyPromoteLogWriter newFamilyPromoteLogWriter;

    @Transactional
    public List<LineOutNewFamily> lineOut(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        // 등반 이전 확인
        newFamilyPromoteLogLookUpManager.findBeforePromotedByNewFamilyIds(newFamilyIds);

        // 라인아웃
        List<LineOutNewFamily> lineOutNewFamilies = newFamilies.stream()
                .map(LineOutNewFamily::from)
                .collect(Collectors.toList());

        newFamilyWriter.deleteByIdIn(newFamilyIds);
        lineOutNewFamilyWriter.saveAll(lineOutNewFamilies);

        // 등반 기록 삭제
        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        newFamilyPromoteLogWriter.deleteByIdIn(promoteLogIds);

        return lineOutNewFamilies;
    }
}
