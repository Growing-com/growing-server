package org.sarangchurch.growing.v2.newfamily.presentation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class RegisterNewFamilyRequest {
    @NotNull
    private String name;
}
