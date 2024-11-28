package org.sarangchurch.growing.v1.feat.newfamily.infra.component.propagate;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyStatus;
import org.sarangchurch.growing.v1.feat.newfamily.infra.data.newfamily.NewFamilyFinder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewFamilyLineUpRequester {
    private final NewFamilyFinder newFamilyFinder;

    @Transactional
    public void requestLineUp(List<Long> newFamilyIds) {
        List<NewFamily> newFamilies = newFamilyFinder.findByIdInOrThrow(newFamilyIds);

        boolean allStateMatch = newFamilies.stream()
                .allMatch(it -> it.statusEquals(NewFamilyStatus.NEW_FAMILY));

        if (!allStateMatch) {
            throw new IllegalStateException("이미 라인업 요청이 완료된 새가족이 포함되어 있습니다.");
        }

        newFamilies.forEach(NewFamily::toLineUpRequest);
    }
}
