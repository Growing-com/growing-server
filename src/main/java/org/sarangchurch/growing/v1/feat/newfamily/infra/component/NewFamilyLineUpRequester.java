package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpRequester {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository newFamilyPromoteLogRepository;

    @Transactional
    public void run(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyRepository.findByIdIn(newFamilyIds);

        if (newFamilyIds.size() != newFamilies.size()) {
            throw new IllegalArgumentException("존재하지 않는 새가족이 포함되어 있습니다.");
        }

        boolean alreadyRequested = newFamilies.stream()
                .anyMatch(el -> el.getNewFamilyPromoteLogId() != null);

        if (alreadyRequested) {
            throw new IllegalStateException("이미 라인업 요청이 완료된 새가족이 포함되어 있습니다.");
        }

        List<NewFamilyPromoteLog> savedLogs = newFamilyPromoteLogRepository.saveAll(NewFamilyPromoteLog.ofSize(newFamilies.size()));

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamilyPromoteLog log = savedLogs.get(i);
            NewFamily newFamily = newFamilies.get(i);

            newFamily.setPromoteLog(log);
        }
    }
}
