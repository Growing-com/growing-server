package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoteLogWriter {
    private final NewFamilyPromoteLogRepository newFamilyPromoteLogRepository;

    public void deleteByIdIn(List<Long> promoteLogIds) {
        newFamilyPromoteLogRepository.deleteByIdIn(promoteLogIds);
    }

    public List<NewFamilyPromoteLog> saveAll(List<NewFamilyPromoteLog> newFamilyPromoteLogs) {
        return newFamilyPromoteLogRepository.saveAll(newFamilyPromoteLogs);
    }
}
