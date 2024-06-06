package org.sarangchurch.growing.v2.newfamily.application.lineup;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.NewFamilyPromoter;
import org.sarangchurch.growing.v2.newfamily.infrastructure.term.TermDownstream;
import org.sarangchurch.growing.v2.term.domain.Term;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LineupService {
    private final NewFamilyRepository newFamilyRepository;
    private final NewFamilyPromoter newFamilyPromoter;
    private final TermDownstream termDownstream;

    public void lineup(Long newFamilyId, LineupRequest request) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        if (request.getPromoteDate() != null) {
            newFamilyPromoter.promoteAndLineup(newFamilyId, request.getPromoteDate(), request.getSmallGroupId());

            return;
        }

        // 등반 없이 일반 순모임 라인업(특이 케이스)
        validateSmallGroup(request.getSmallGroupId());
        newFamily.assignSmallGroup(request.getSmallGroupId());
    }

    private void validateSmallGroup(Long smallGroupId) {
        Term term = termDownstream.findTermBySmallGroupId(smallGroupId);

        if (!term.isActive()) {
            throw new IllegalStateException("종료됐거나 시작되지 않은 텀의 순모임에 새가족을 배정할 수 없습니다.");
        }
    }
}
