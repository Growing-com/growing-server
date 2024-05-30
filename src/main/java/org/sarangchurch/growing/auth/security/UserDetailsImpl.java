package org.sarangchurch.growing.auth.security;

import org.sarangchurch.growing.user.domain.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserDetailsImpl extends User {
    private final Long id;
    private final String name;

    /**
     * For Spring Rest Docs
     */
    public UserDetailsImpl() {
        super("1", "1", List.of());
        this.id = 1L;
        this.name = "1";
    }

    public UserDetailsImpl(UserEntity user) {
        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
        this.id = user.getId();
        this.name = user.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
