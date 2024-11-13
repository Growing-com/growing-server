package org.sarangchurch.growing.v1.feat.user.infra.stream;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.term.SmallGroupMemberService;
import org.springframework.stereotype.Component;

@Component("User_SmallGroupMember_Upstream")
@RequiredArgsConstructor
public class SmallGroupMemberUpstream {
    private final SmallGroupMemberService smallGroupMemberService;

    public void create(Long userId, Long smallGroupId) {
        smallGroupMemberService.create(userId, smallGroupId);
    }

    public void update(Long userId, Long smallGroupId) {
        smallGroupMemberService.update(userId, smallGroupId);
    }
}
