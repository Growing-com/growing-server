package org.sarangchurch.growing.term.application.facade;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.types.Week;
import org.sarangchurch.growing.term.application.dto.AddNewComerToTeamRequest;
import org.sarangchurch.growing.term.application.dto.RegisterNewComerRequest;
import org.sarangchurch.growing.term.application.service.TeamService;
import org.sarangchurch.growing.term.infra.UserDownStream;
import org.sarangchurch.growing.user.application.service.ValidateAvailableService;
import org.sarangchurch.growing.user.domain.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class RegisterNewComerFacade {

    private final UserDownStream userDownStream;
    private final PasswordEncoder passwordEncoder;
    private final ValidateAvailableService validateAvailableService;
    private final TeamService teamService;

    public void register(RegisterNewComerRequest request) {
        String randUsername = UUID.randomUUID().toString().substring(0, 20);
        String randPassword = UUID.randomUUID().toString().substring(0, 20);

        validateAvailableService.validateUsernameAvailable(randUsername);
        validateAvailableService.validateNameAvailable(request.getName());

        UserEntity savedUser = userDownStream.save(request.toEntity(
                randUsername,
                passwordEncoder.encode(randPassword),
                Week.previousSunday(request.getVisitDate())
        ));

        teamService.addNewComer(new AddNewComerToTeamRequest(request.getTeamId(), savedUser.getId()));
    }
}
