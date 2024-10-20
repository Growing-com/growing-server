package org.sarangchurch.growing.v1.feat.newfamily.infra.data;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroup;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilygroup.NewFamilyGroupRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewFamilyGroupWriter {
    private final NewFamilyGroupRepository newFamilyGroupRepository;

    public NewFamilyGroup save(NewFamilyGroup newFamilyGroup) {
        return newFamilyGroupRepository.save(newFamilyGroup);
    }

    public void deleteById(Long newFamilyGroupId) {
        newFamilyGroupRepository.deleteById(newFamilyGroupId);
    }
}
