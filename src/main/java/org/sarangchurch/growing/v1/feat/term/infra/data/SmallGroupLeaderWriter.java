package org.sarangchurch.growing.v1.feat.term.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeader;
import org.sarangchurch.growing.v1.feat.term.domain.smallgroupleader.SmallGroupLeaderRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupLeaderWriter {
    private final SmallGroupLeaderRepository smallGroupLeaderRepository;

    public void deleteById(Long id) {
        smallGroupLeaderRepository.deleteById(id);
    }

    public SmallGroupLeader save(SmallGroupLeader smallGroupLeader) {
        return smallGroupLeaderRepository.save(smallGroupLeader);
    }
}
