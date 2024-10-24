package org.sarangchurch.growing.v1.feat.newfamily.infra.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NewFamilyUpdater {
    private final NewFamilyFinder newFamilyFinder;
    private final NewFamilyGroupValidator newFamilyGroupValidator;

    @Transactional
    public NewFamily update(
            Long newFamilyId,
            Long targetNewFamilyGroupId,
            LocalDate visitDate,
            NewFamilyEtc etc
    ) {
        NewFamily newFamily = newFamilyFinder.findByIdOrThrow(newFamilyId);

        boolean isNewFamilyGroupIdChanged = !Objects.equals(newFamily.getNewFamilyGroupId(), targetNewFamilyGroupId);

        if (isNewFamilyGroupIdChanged) {
            if (targetNewFamilyGroupId != null) {
                newFamilyGroupValidator.validateAvailable(targetNewFamilyGroupId);
            }

            newFamily.updateNewFamilyGroup(targetNewFamilyGroupId);
        }

        newFamily.updateEtc(etc);
        newFamily.updateVisitDate(visitDate);

        return newFamily;
    }
}
