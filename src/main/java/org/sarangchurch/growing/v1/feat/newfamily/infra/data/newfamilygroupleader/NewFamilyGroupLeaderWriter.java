package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamilygroupleader;

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

    public void deleteById(Long newFamilyGroupLeaderId) {
        newFamilyGroupLeaderRepository.deleteById(newFamilyGroupLeaderId);
    }
}
