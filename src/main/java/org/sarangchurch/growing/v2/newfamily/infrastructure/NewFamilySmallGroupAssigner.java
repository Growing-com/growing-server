package org.sarangchurch.growing.v2.newfamily.infrastructure;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.term.TermDownstream;
import org.sarangchurch.growing.v2.term.domain.Term;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilySmallGroupAssigner {
    private final NewFamilyRepository newFamilyRepository;
    private final TermDownstream termDownstream;

    @Transactional
    public void assign(Long newFamilyId, Long smallGroupId) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족입니다"));

        Term term = termDownstream.findTermBySmallGroupId(smallGroupId);

        if (!term.isActive()) {
            throw new IllegalStateException("종료되거나 시작되지 않은 텀의 순모임에 새가족을 배정할 수 없습니다.");
        }

        newFamily.assignSmallGroup(smallGroupId);
    }
}
