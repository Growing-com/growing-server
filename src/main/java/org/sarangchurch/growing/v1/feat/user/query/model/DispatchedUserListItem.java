package org.sarangchurch.growing.v1.feat.user.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Sex;
import org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser.DispatchType;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class DispatchedUserListItem {
    private final Long dispatchedUserId;
    private final String name;
    private final Sex sex;
    private final LocalDate birth;
    private final Integer grade;
    private final DispatchType type;
    private final LocalDate sendDate;
    private final LocalDate returnDate;
}
