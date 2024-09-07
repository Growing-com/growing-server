package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.promote.V1PromoteRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class V1PromoteManager {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository promoteLogRepository;

    @Transactional
    public void promote(V1PromoteRequest request) {
        // 새가족 존재 검증
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(V1PromoteRequest.V1PromoteRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(newFamilyIds);

        if (newFamilyIds.size() != newFamilies.size()) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다.");
        }

        // 등반 가능 여부 검증
        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = promoteLogRepository.findByIdIn(promoteLogIds);

        long promoteCandidateCount = promoteLogs.stream()
                .filter(NewFamilyPromoteLog::isPromoteCandidate)
                .count();

        if (promoteCandidateCount != newFamilyIds.size()) {
            throw new IllegalArgumentException("등반 후보가 아닌 새가족이 포함되어 있습니다.");
        }

        // 등반 처리
        for (V1PromoteRequest.V1PromoteRequestItem requestItem : request.getContent()) {
            Long newFamilyId = requestItem.getNewFamilyId();
            LocalDate promoteDate = requestItem.getPromoteDate();

            NewFamily newFamily = newFamilies.stream()
                    .filter(el -> el.getId().equals(newFamilyId))
                    .findAny()
                    .orElseThrow();

            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            log.updatePromoteDate(promoteDate);
        }
    }
}
