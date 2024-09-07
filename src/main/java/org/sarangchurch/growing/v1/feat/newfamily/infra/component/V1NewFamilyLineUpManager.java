package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.core.interfaces.term.V1SmallGroupService;
import org.sarangchurch.growing.v1.feat.newfamily.application.lineup.V1LineUpRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class V1NewFamilyLineUpManager {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository promoteLogRepository;
    private final V1SmallGroupService smallGroupService;

    @Transactional
    public void lineUp(V1LineUpRequest request) {
        List<Long> newFamilyIds = request.getContent()
                .stream()
                .map(V1LineUpRequest.V1LineUpRequestItem::getNewFamilyId)
                .collect(Collectors.toList());

        // 새가족 존재 검증
        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(newFamilyIds);

        if (newFamilyIds.size() != newFamilies.size()) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다.");
        }

        // 새가족 라인업 가능 검증
        boolean containsBeforeRequest = newFamilies.stream()
                .anyMatch(el -> el.getNewFamilyPromoteLogId() == null);

        if (containsBeforeRequest) {
            throw new IllegalStateException("라인업 요청이 되지 않은 새가족이 포함되어 있습니다.");
        }

        List<Long> promoteLogIds = newFamilies.stream()
                .map(NewFamily::getNewFamilyPromoteLogId)
                .collect(Collectors.toList());

        List<NewFamilyPromoteLog> promoteLogs = promoteLogRepository.findByIdIn(promoteLogIds);

        boolean containsPromoted = promoteLogs.stream()
                .anyMatch(NewFamilyPromoteLog::isPromoted);

        if (containsPromoted) {
            throw new IllegalStateException("이미 등반된 새가족이 포함되어 있습니다.");
        }

        // 일반 순모임 가용 검증
        List<Long> smallGroupIds = request.getContent()
                .stream()
                .map(V1LineUpRequest.V1LineUpRequestItem::getSmallGroupId)
                .collect(Collectors.toList());

        smallGroupService.validateAvailable(smallGroupIds);

        // promote-log에 일반 순모임 세팅
        for (V1LineUpRequest.V1LineUpRequestItem requestItem : request.getContent()) {
            Long smallGroupId = requestItem.getSmallGroupId();

            NewFamily newFamily = newFamilies.stream()
                    .filter(el -> el.getId().equals(requestItem.getNewFamilyId()))
                    .findAny()
                    .orElseThrow();

            NewFamilyPromoteLog log = promoteLogs.stream()
                    .filter(el -> el.getId().equals(newFamily.getNewFamilyPromoteLogId()))
                    .findAny()
                    .orElseThrow();

            log.updateSmallGroupId(smallGroupId);
        }
    }
}
