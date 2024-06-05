package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LineupService {
    private final NewFamilyRepository newFamilyRepository;

    public void lineup(Long newFamilyId, LineupRequest request) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        Long smallGroupId = request.getSmallGroupId();

        // TODO: 새가족을
        if (request.getPromoteDate() != null) {
            // 등반 + 라인업

        } else {
            // 라인업
        }
    }
}
