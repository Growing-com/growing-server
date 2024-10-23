package org.sarangchurch.growing.v1.feat.newfamily.application.update;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final NewFamilyUpdater newFamilyUpdater;

    public void update(Long newFamilyId, UpdateRequest request) {
        newFamilyUpdater.update(
                newFamilyId,
                request.getNewFamilyGroupId(),
                request.getName(),
                request.getSex(),
                request.getPhoneNumber(),
                request.getBirth(),
                request.getVisitDate(),
                request.getGrade(),
                request.getEtc()
        );
    }
}
