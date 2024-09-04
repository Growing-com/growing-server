package org.sarangchurch.growing.v1.feat.newfamily.application.update;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.V1NewFamilyUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class V1UpdateService {
    private final V1NewFamilyUpdater updater;

    public void update(Long newFamilyId, V1UpdateRequest request) {
        updater.update(newFamilyId, request);
    }
}
