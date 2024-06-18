package org.sarangchurch.growing.v2.feat.newfamily.infrastructure.component;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.feat.newfamily.application.updateinfo.UpdateInfoRequest;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyRepository;
import org.sarangchurch.growing.v2.feat.newfamily.infrastructure.stream.user.UserUpstream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NewFamilyUpdater {
    private final NewFamilyRepository newFamilyRepository;
    private final UserUpstream userUpstream;

    @Transactional
    public void update(Long newFamilyId, UpdateInfoRequest request) {
        NewFamily newFamily = newFamilyRepository.findById(newFamilyId)
                .orElseThrow(() -> new IllegalArgumentException("새가족을 찾을 수 없습니다"));

        if (request.getEtc() != null) {
            newFamily.updateEtc(request.getEtc());
        }

        userUpstream.update(
                newFamily.getUserId(),
                request.getName(),
                request.getPhoneNumber(),
                request.getBirth(),
                request.getGender(),
                request.getGrade()
        );
    }
}
