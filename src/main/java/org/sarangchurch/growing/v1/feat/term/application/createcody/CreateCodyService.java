package org.sarangchurch.growing.v1.feat.term.application.createcody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.infra.component.cody.CodyAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCodyService {
    private final CodyAppender codyAppender;

    public void create(Long termId, CreateCodyRequest request) {
        codyAppender.append(termId, request.getCodyUserId());
    }
}
