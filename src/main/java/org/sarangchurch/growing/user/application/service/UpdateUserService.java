package org.sarangchurch.growing.user.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.user.application.dto.UpdateUserRequest;
import org.sarangchurch.growing.user.domain.UserEntity;
import org.sarangchurch.growing.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserService {

    private final UserRepository userRepository;
    private final ValidateAvailableService validateAvailableService;

    public void update(Long userId, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        validateAvailableService.validateNameChangeable(user, request.getName());

        user.update(
                request.getName(),
                request.getSex(),
                request.getPhoneNumber(),
                request.getBirth() != null ? request.getBirth() : user.getBirth(),
                request.getGrade(),
                request.getIsActive(),
                request.getVisitDate() != null ? request.getVisitDate() : user.getVisitDate(),
                request.getEtc()
        );
    }
}
