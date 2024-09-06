package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineout.V1LineOutRequest;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily.LineoutNewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class V1NewFamilyLineOutManager {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository promoteLogRepository;
    private final LineoutNewFamilyRepository lineoutNewFamilyRepository;

    @Transactional
    public void lineOut(V1LineOutRequest request) {
        // 새가족 검증
        List<Long> newFamilyIds = request.getNewFamilyIds();

        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(newFamilyIds);

        if (newFamilyIds.size() != newFamilies.size()) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다.");
        }

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
        List<LineoutNewFamily> lineoutNewFamilies = newFamilies.stream()
                .map(LineoutNewFamily::from)
                .collect(Collectors.toList());

        newFamilyRepository.deleteByIdIn(newFamilyIds);
        lineoutNewFamilyRepository.saveAll(lineoutNewFamilies);

        // 등반 기록 삭제
         promoteLogRepository.deleteByIdIn(promoteLogIds);
    }
}
