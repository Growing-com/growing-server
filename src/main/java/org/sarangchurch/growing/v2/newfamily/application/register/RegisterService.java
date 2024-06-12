package org.sarangchurch.growing.v2.newfamily.application.register;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v2.newfamily.domain.NewFamilyRepository;
import org.sarangchurch.growing.v2.newfamily.infrastructure.component.NewFamilyGroupValidator;
import org.sarangchurch.growing.v2.newfamily.infrastructure.stream.user.UserUpstream;
import org.sarangchurch.growing.v2.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {
    private final NewFamilyGroupValidator newFamilyGroupValidator;
    private final NewFamilyRepository newFamilyRepository;
    private final UserUpstream userUpstream;

    public void register(RegisterRequest request) {
        if (request.getNewFamilyGroupId() != null) {
            newFamilyGroupValidator.validateAvailable(request.getNewFamilyGroupId());
        }

        User user = User.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birth(request.getBirth())
                .gender(request.getGender())
                .grade(request.getGrade())
                .build();

        User savedUser = userUpstream.register(user);

        newFamilyRepository.save(request.toEntity(savedUser.getId()));
    }

}
