package org.sarangchurch.growing.v2.newfamily.application.updateinfo;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.infrastructure.component.NewFamilyUpdater;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateInfoService {
    private final NewFamilyUpdater newFamilyUpdater;

    public void update(Long newFamilyId, UpdateInfoRequest request) {
        newFamilyUpdater.update(newFamilyId, request);
    }
}
