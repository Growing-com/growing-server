package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term.V1TermDownstream;
import org.sarangchurch.growing.v2.feat.term.domain.term.Term;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class V1NewFamilyGroupValidator {
    private final NewFamilyGroupRepository newFamilyGroupRepository;
    private final V1TermDownstream termDownstream;

    public void validateAvailable(Long newFamilyGroupId) {
        NewFamilyGroup newFamilyGroup = newFamilyGroupRepository.findById(newFamilyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 새가족반입니다."));

        Term term = termDownstream.findTerm(newFamilyGroup.getTermId());

        if (!term.isActive()) {
            throw new IllegalStateException("종료되거나 시작하지 않은 텀에 라인업할 수 없습니다.");
        }
    }
}
