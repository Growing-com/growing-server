package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.application.update.UpdateRequest;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NewFamilyUpdater {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyGroupValidator newFamilyGroupValidator;
    private final UserUpstream userUpstream;

    @Transactional
    public void update(Long newFamilyId, UpdateRequest request) {
        NewFamily newFamily = newFamilyFinder.findByIdOrThrow(newFamilyId);

        Long requestNewFamilyGroupId = request.getNewFamilyGroupId();
        boolean isNewFamilyGroupIdChanged = !Objects.equals(newFamily.getNewFamilyGroupId(), requestNewFamilyGroupId);

        if (isNewFamilyGroupIdChanged) {
            if (requestNewFamilyGroupId != null) {
                newFamilyGroupValidator.validateAvailable(requestNewFamilyGroupId);
            }

            newFamily.updateNewFamilyGroup(requestNewFamilyGroupId);
        }

        if (request.getEtc() != null) {
            newFamily.updateEtc(request.getEtc());
        }

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
