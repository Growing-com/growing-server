package org.sarangchurch.growing.v1.feat.term.infra.stream.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.v1.newfamily.NewFamilyGroupLeaderService;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderUpstream {
    private final NewFamilyGroupLeaderService newFamilyGroupLeaderService;

    public NewFamilyGroupLeader save(NewFamilyGroupLeader leader) {
        return newFamilyGroupLeaderService.save(leader);
    }
}
