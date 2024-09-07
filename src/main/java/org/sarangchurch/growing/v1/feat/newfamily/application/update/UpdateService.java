package org.sarangchurch.growing.v1.feat.newfamily.application.update;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final NewFamilyUpdater updater;

    public void update(Long newFamilyId, UpdateRequest request) {
        updater.update(newFamilyId, request);
    }
}
