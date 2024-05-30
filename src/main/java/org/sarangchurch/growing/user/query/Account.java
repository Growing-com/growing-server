package org.sarangchurch.growing.user.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.domain.team.Duty;
import org.sarangchurch.growing.user.domain.Role;
import org.sarangchurch.growing.user.domain.Sex;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Account {
    private final Long id;
    private final String name;
    private final Integer grade;
    private final Sex sex;
    private final Duty duty;
    private final Role role;
    private final String phoneNumber;
    private final LocalDate birth;
    private final Boolean isActive;
    private final LocalDate visitDate;
    private final String etc;
    private final LocalDateTime updatedAt;
    private final String updatedBy;
}
