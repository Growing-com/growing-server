package org.sarangchurch.growing.v1.feat.term.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.sarangchurch.growing.v1.feat.term.infra.component.SmallGroupMemberAppender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmallGroupMemberServiceImpl implements SmallGroupMemberService {
    private final SmallGroupMemberAppender smallGroupMemberAppender;

    @Override
    public void create(Long userId, Long smallGroupId) {
        smallGroupMemberAppender.append(userId, smallGroupId);
    }
}
