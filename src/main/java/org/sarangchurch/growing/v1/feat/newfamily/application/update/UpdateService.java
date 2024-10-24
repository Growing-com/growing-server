package org.sarangchurch.growing.v1.feat.newfamily.application.update;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.NewFamilyUpdater;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateService {
    private final NewFamilyUpdater newFamilyUpdater;
    private final UserUpstream userUpstream;

    @Transactional
    public void update(Long newFamilyId, UpdateRequest request) {
        NewFamily newFamily = newFamilyUpdater.update(
                newFamilyId,
                request.getNewFamilyGroupId(),
                request.getVisitDate(),
                request.getEtc()
        );

        userUpstream.update(
                newFamily.getUserId(),
                request.getName(),
                request.getPhoneNumber(),
                request.getBirth(),
                request.getSex(),
                request.getGrade()
        );
    }
}
