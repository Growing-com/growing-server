package org.sarangchurch.growing.v1.feat.auth.domain;

import org.springframework.security.core.userdetails.User;

import java.util.List;

public class Principal extends User {
    private final Long id;
    private final Long userId;
    private final boolean isSuperUser;

    public static Principal from(Account account) {
        return new Principal(account);
    }

    Principal(Account account) {
        super(account.getUsername(), account.getPassword(), List.of());
        this.id = account.getId();
        this.userId = account.getUserId();
        this.isSuperUser = account.isSuperUser();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isSuperUser() {
        return isSuperUser;
    }
}
