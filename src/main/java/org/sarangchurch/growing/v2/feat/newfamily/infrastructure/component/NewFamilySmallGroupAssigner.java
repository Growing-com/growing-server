package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.V1TermDownstream;
import org.sarangchurch.growing.v2.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilySmallGroupAssigner {
    private final NewFamilyRepository newFamilyRepository;
    private final V1TermDownstream termDownstream;

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
