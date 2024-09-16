package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.promote.PromoteRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoter {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogRepository promoteLogRepository;

    @Transactional
    public void promote(PromoteRequest request) {
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(PromoteRequest.V1PromoteRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<NewFamily> newFamilies = newFamilyFinder.findByIdIn(newFamilyIds);

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
        for (PromoteRequest.V1PromoteRequestItem requestItem : request.getContent()) {
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
