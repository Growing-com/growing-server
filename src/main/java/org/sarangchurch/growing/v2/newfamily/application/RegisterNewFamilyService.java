package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamily;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterNewFamilyService {

    private final NewFamilyRepository newFamilyRepository;

    public void register(RegisterNewFamilyRequest request) {
        validateNewFamilyGroup(request.getNewFamilyGroupId());

        // TODO: 지체(User) 등록
        Long userId = 1L;

        NewFamily newFamily = newFamilyRepository.save(request.toEntity(userId));
    }

    private void validateNewFamilyGroup(Long newFamilyGroupId) {
        // TODO: 새가족반 검증
    }
}
