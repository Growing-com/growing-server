package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilypromotelog;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLog;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog.NewFamilyPromoteLogRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyPromoteLogFinder {
    private final NewFamilyPromoteLogRepository promoteLogRepository;


    public List<NewFamilyPromoteLog> findByIdIn(List<Long> promoteLogIds) {
        return promoteLogRepository.findByIdIn(promoteLogIds);
    }

    public boolean containsPromotedBySmallGroupId(Long smallGroupId) {
        return promoteLogRepository.existsBySmallGroupId(smallGroupId);
    }
}
