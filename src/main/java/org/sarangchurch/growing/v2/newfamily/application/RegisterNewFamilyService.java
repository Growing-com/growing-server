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
        validateNewFamilyLeader(request.getNewFamilyLeaderId());

        newFamilyRepository.save(request.toEntity());
    }

    private void validateNewFamilyLeader(Long newFamilyLeaderId) {
        // TODO: 새가족반 순장 id 검증
    }
}
