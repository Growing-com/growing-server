package org.sarangchurch.growing.v1.feat.user.query.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.user.domain.account.AccountRole;

@Getter
@RequiredArgsConstructor
public class UserAccount {
    private final Long userId;
    private final String name;
    private final AccountRole role;
}
