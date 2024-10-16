package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.cody.CodyRepository;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupDownstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CodyRemover {
    private final CodyRepository codyRepository;
    private final SmallGroupRepository smallGroupRepository;
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;

    @Transactional
    public void remove(Long codyId) {
        Cody cody = codyRepository.findById(codyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코디입니다."));

        long smallGroupCount = smallGroupRepository.countByCodyId(cody.getId());

        if (smallGroupCount > 0) {
            throw new IllegalStateException("담당하는 순모임이 있는 코디는 삭제할 수 없습니다.");
        }

        long newFamilyGroupCount = newFamilyGroupDownstream.countByCodyId(cody.getId());

        if (newFamilyGroupCount > 0) {
            throw new IllegalStateException("담당하는 새가족반이 있는 코디는 삭제할 수 없습니다.");
        }

        codyRepository.deleteById(cody.getId());
    }
}
