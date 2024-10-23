package org.sarangchurch.growing.v1.feat.term.application.createpastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.PastorAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePastorService {
    private final PastorAppender pastorAppender;

    public void create(Long termId, CreatePastorRequest request) {
        pastorAppender.append(termId, request.getPastorUserId());
    }
}
