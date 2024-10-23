package org.sarangchurch.growing.v1.feat.term.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.infra.data.PastorFinder;
import org.sarangchurch.growing.v1.feat.term.infra.data.PastorWriter;
import org.sarangchurch.growing.v1.feat.term.infra.data.TermFinder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PastorRemover {
    private final PastorFinder pastorFinder;
    private final TermFinder termFinder;
    private final PastorWriter pastorWriter;

    public void remove(Long pastorId) {
        Pastor pastor = pastorFinder.findByIdOrThrow(pastorId);

        termFinder.findActiveByIdOrThrow(pastor.getTermId());

        if (pastor.isSenior()) {
            throw new IllegalStateException("담당 교역자는 삭제할 수 없습니다. 먼저 담당 교역자를 변경해주세요.");
        }

        pastorWriter.deleteById(pastorId);
    }
}
