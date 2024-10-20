package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.infra.data.CodyFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.CodyWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.SmallGroupFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily.NewFamilyGroupDownstream;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodyRemover {
    private final CodyFinder codyFinder;
    private final TermFinder termFinder;
    private final SmallGroupFinder smallGroupFinder;
    private final NewFamilyGroupDownstream newFamilyGroupDownstream;
    private final CodyWriter codyWriter;

    public void remove(Long codyId) {
        Cody cody = codyFinder.findByIdOrThrow(codyId);

        // 활성 텀 검증
        termFinder.findActiveByIdOrThrow(cody.getTermId());

        // 담당 일반 순모임 검증
        long smallGroupCount = smallGroupFinder.countByCodyId(codyId);

        if (smallGroupCount > 0) {
            throw new IllegalStateException("담당하는 순모임이 있는 코디는 삭제할 수 없습니다.");
        }

        // 담당 새가족반 검증
        long newFamilyGroupCount = newFamilyGroupDownstream.countByCodyId(cody.getId());

        if (newFamilyGroupCount > 0) {
            throw new IllegalStateException("담당하는 새가족반이 있는 코디는 삭제할 수 없습니다.");
        }

        codyWriter.delete(cody);
    }
}