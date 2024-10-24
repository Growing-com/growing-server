package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilypromotelog.NewFamilyPromoteLogWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpRequester {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyPromoteLogWriter newFamilyPromoteLogWriter;

    @Transactional
    public void request(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean alreadyRequested = newFamilies.stream()
                .anyMatch(NewFamily::hasPromoteLog);

        if (alreadyRequested) {
            throw new IllegalStateException("이미 라인업 요청이 완료된 새가족이 포함되어 있습니다.");
        }

        List<NewFamilyPromoteLog> savedLogs = newFamilyPromoteLogWriter.saveAll(NewFamilyPromoteLog.ofSize(newFamilies.size()));

        for (int i = 0; i < newFamilies.size(); i++) {
            NewFamily newFamily = newFamilies.get(i);
            NewFamilyPromoteLog log = savedLogs.get(i);

            newFamily.setPromoteLog(log);
        }
    }
}
