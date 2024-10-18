package org.sarangchurch.growing.v1.feat.term.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroup;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroup.SmallGroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupWriter {
    private final SmallGroupRepository smallGroupRepository;

    public void deleteById(Long id) {
        smallGroupRepository.deleteById(id);
    }

    public SmallGroup save(SmallGroup smallGroup) {
        return smallGroupRepository.save(smallGroup);
    }
}
