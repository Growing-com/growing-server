package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromoteService {
    private final NewFamilyRepository newFamilyRepository;

    public void promote(Long newFamilyId, PromoteRequest request) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        newFamily.promote(request.getPromoteDate());

        Long smallGroupLeaderId = request.getSmallGroupLeaderId();

        if (smallGroupLeaderId != null) {
            // TODO: 라인업 처리
        }
    }
}
