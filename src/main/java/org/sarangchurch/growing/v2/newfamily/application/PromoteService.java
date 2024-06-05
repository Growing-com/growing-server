package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLog;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyPromoteLogRepository;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromoteService {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoteLogRepository newFamilyPromoteLogRepository;

    public void promote(Long newFamilyId, PromoteRequest request) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        NewFamilyPromoteLog promoteLog = request.createPromoteLog();

        if (promoteLog.getSmallGroupId() != null) {
            validateSmallGroup(promoteLog.getSmallGroupId());
        }

        NewFamilyPromoteLog log = newFamilyPromoteLogRepository.save(promoteLog);

        newFamily.promote(log.getId());
    }

    private void validateSmallGroup(Long smallGroupId) {
        // TODO
    }
}
