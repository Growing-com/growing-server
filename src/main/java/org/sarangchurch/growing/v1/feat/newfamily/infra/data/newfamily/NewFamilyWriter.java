package org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyWriter {
    private final NewFamilyRepository newFamilyRepository;

    public NewFamily save(NewFamily newFamily) {
        return newFamilyRepository.save(newFamily);
    }

    public void deleteByIdIn(List<Long> newFamilyIds) {
        newFamilyRepository.deleteByIdIn(newFamilyIds);
    }
}
