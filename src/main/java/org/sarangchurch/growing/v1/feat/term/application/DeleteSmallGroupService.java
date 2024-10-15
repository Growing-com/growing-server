package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupRemover;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteSmallGroupService {
    private final SmallGroupRemover smallGroupRemover;

    public void delete(Long id) {
        smallGroupRemover.remove(id);
    }
}
