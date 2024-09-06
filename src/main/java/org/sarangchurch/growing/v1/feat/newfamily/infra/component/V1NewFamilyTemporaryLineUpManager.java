package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.V1SmallGroupService;
import org.sarangchurch.growing.v1.feat.newfamily.application.temporarylineup.V1TemporaryLineUpRequest;
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
public class V1NewFamilyTemporaryLineUpManager {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository promoteLogRepository;
    // TODO: DownStream
    private final V1SmallGroupService smallGroupService;

    // TODO: 중복 제거
    @Transactional
    public void temporaryLineUp(V1TemporaryLineUpRequest request) {
        // 새가족 검증
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(V1TemporaryLineUpRequest.V1TemporaryLineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(newFamilyIds);

        if (newFamilyIds.size() != newFamilies.size()) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다.");
        }

        // 라인업 가능 검증
        boolean containsBeforeRequest = newFamilies.stream()
                .anyMatch(el -> el.getNewFamilyPromoteLogId() == null);

        if (containsBeforeRequest) {
            throw new IllegalStateException("라인업 요청이 되지 않은 새가족이 포함되어 있습니다.");
        }

        // 등반 이전 검증
        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = promoteLogRepository.findByIdIn(promoteLogIds);

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }

        // 후보 순장 검증
        List<Long> smallGroupIds = request.getContent()
                .stream()
                .flatMap(el -> el.getTemporarySmallGroupIds().stream())
                .collect(Collectors.toList());

        smallGroupService.validateAvailable(smallGroupIds);

        // 후보 순장 배정
        for (V1TemporaryLineUpRequest.V1TemporaryLineUpRequestItem requestItem : request.getContent()) {
            Long newFamilyId = requestItem.getNewFamilyId();
            List<Long> temporarySmallGroupIds = requestItem.getTemporarySmallGroupIds();

            NewFamily newFamily = newFamilies.stream()
                    .filter(el -> el.getId().equals(newFamilyId))
                    .findAny()
                    .orElseThrow();

            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            log.updateTemporarySmallGroups(temporarySmallGroupIds);
        }
    }
}
