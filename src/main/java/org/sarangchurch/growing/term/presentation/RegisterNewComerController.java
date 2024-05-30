package org.sarangchurch.growing.term.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.term.application.dto.RegisterNewComerRequest;
import org.sarangchurch.growing.term.application.facade.RegisterNewComerFacade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterNewComerController {

    private final RegisterNewComerFacade registerNewComerFacade;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/user")
    public void registerNewComer(@RequestBody @Valid RegisterNewComerRequest request) {
        registerNewComerFacade.register(request);
    }
}
