package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeader;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroupleader.NewFamilyGroupLeaderRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupLeaderWriter {
    private final NewFamilyGroupLeaderRepository newFamilyGroupLeaderRepository;

    public NewFamilyGroupLeader save(NewFamilyGroupLeader newFamilyGroupLeader) {
        return newFamilyGroupLeaderRepository.save(newFamilyGroupLeader);
    }
}
