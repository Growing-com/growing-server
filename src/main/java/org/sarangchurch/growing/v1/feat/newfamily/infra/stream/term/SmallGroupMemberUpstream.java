package org.sarangchurch.growing.v1.feat.newfamily.infra.stream.term;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmallGroupMemberUpstream {
    private final SmallGroupMemberService smallGroupMemberService;

    public void create(Long userId, Long smallGroupId) {
        smallGroupMemberService.create(userId, smallGroupId);
    }
}
