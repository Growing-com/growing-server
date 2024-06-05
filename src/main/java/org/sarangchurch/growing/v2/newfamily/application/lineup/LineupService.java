package org.sarangchurch.growing.v2.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyPromoter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LineupService {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoter newFamilyPromoter;

    public void lineup(Long newFamilyId, LineupRequest request) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        if (request.getPromoteDate() != null) {
            newFamilyPromoter.promote(newFamilyId, request.getPromoteDate(), request.getSmallGroupId());
            // TODO: 라인업(일반 케이스, 일반 순원 생성)
        } else {
            // 라인업(특이 케이스)
            validateSmallGroup(request.getSmallGroupId());
            newFamily.assignSmallGroup(request.getSmallGroupId());
        }
    }

    private void validateSmallGroup(Long smallGroupId) {
        // TODO: 순모임 유효성 검사
    }
}
