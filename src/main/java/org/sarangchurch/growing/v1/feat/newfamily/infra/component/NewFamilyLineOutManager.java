package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.LineOutRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyLineOutManager {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository promoteLogRepository;
    private final LineOutNewFamilyRepository lineoutNewFamilyRepository;

    @Transactional
    public List<LineOutNewFamily> lineOut(LineOutRequest request) {
        // 새가족 검증
        List<Long> newFamilyIds = request.getNewFamilyIds();

        List<NewFamily> newFamilies = newFamilyFinder.findByIdIn(newFamilyIds);

        // 라인아웃 가능 여부 검증(등반전)
        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = promoteLogRepository.findByIdIn(promoteLogIds);

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }

        // 라인아웃
        List<LineOutNewFamily> lineOutNewFamilies = newFamilies.stream()
                .map(LineOutNewFamily::from)
                .collect(Collectors.toList());

        newFamilyRepository.deleteByIdIn(newFamilyIds);
        lineoutNewFamilyRepository.saveAll(lineOutNewFamilies);

        // 등반 기록 삭제
         promoteLogRepository.deleteByIdIn(promoteLogIds);

         return lineOutNewFamilies;
    }
}
