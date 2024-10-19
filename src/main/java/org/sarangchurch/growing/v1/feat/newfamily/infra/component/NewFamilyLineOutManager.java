package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.LineOutRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyLineOutManager {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogFinder newFamilyPromoteLogFinder;

    private final NewFamilyWriter newFamilyWriter;
    private final LineOutNewFamilyWriter lineOutNewFamilyWriter;
    private final NewFamilyPromoteLogWriter newFamilyPromoteLogWriter;

    @Transactional
    public List<LineOutNewFamily> lineOut(LineOutRequest request) {
        List<Long> newFamilyIds = request.getNewFamilyIds();

        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        // 등반전 확인
        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        boolean containsPromoted = newFamilyPromoteLogFinder
                .findPromoteCandidatesByNewFamilyIds(newFamilyIds)
                .getSecond()
                .stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }

        // 라인아웃
        List<LineOutNewFamily> lineOutNewFamilies = newFamilies.stream()
                .map(LineOutNewFamily::from)
                .collect(Collectors.toList());

        newFamilyWriter.deleteByIdIn(newFamilyIds);
        lineOutNewFamilyWriter.saveAll(lineOutNewFamilies);

        // 등반 기록 삭제
        newFamilyPromoteLogWriter.deleteByIdIn(promoteLogIds);

        return lineOutNewFamilies;
    }
}
