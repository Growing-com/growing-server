package org.sarangchurch.growing.v2.newfamily.application;

import lombok.RequiredArgsConstructor;
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

        newFamilyRepository.save(request.toEntity());

        // TODO: 지체(User) 등록
    }

    private void validateNewFamilyGroup(Long newFamilyGroupId) {
        // TODO: 새가족반 검증
    }
}
