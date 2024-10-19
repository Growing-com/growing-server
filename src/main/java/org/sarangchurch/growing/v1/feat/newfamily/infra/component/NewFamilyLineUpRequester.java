package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyPromoteLogWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpRequester {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogWriter newFamilyPromoteLogWriter;

    @Transactional
    public void run(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean alreadyRequested = newFamilies.stream()
                .anyMatch(el -> el.getNewFamilyPromoteLogId() != null);

        if (alreadyRequested) {
            throw new IllegalStateException("이미 라인업 요청이 완료된 새가족이 포함되어 있습니다.");
        }

        List<NewFamilyPromoteLog> savedLogs = newFamilyPromoteLogWriter.saveAll(NewFamilyPromoteLog.ofSize(newFamilies.size()));

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamilyPromoteLog log = savedLogs.get(i);
            NewFamily newFamily = newFamilies.get(i);

            newFamily.setPromoteLog(log);
        }
    }
}
