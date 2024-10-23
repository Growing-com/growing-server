package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NewFamilyUpdater {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyGroupValidator newFamilyGroupValidator;
    private final UserUpstream userUpstream;

    @Transactional
    public void update(
            Long newFamilyId,
            Long newFamilyGroupId,
            String name,
            Sex sex,
            String phoneNumber,
            LocalDate birth,
            LocalDate visitDate,
            Integer grade,
            NewFamilyEtc etc
    ) {
        NewFamily newFamily = newFamilyFinder.findByIdOrThrow(newFamilyId);

        boolean isNewFamilyGroupIdChanged = !Objects.equals(newFamily.getNewFamilyGroupId(), newFamilyGroupId);

        if (isNewFamilyGroupIdChanged) {
            if (newFamilyGroupId != null) {
                newFamilyGroupValidator.validateAvailable(newFamilyGroupId);
            }

            newFamily.updateNewFamilyGroup(newFamilyGroupId);
        }

        if (etc != null) {
            newFamily.updateEtc(etc);
        }

        if (visitDate != null) {
            newFamily.updateVisitDate(visitDate);
        }

        userUpstream.update(
                newFamily.getUserId(),
                name,
                phoneNumber,
                birth,
                sex,
                grade
        );
    }
}
