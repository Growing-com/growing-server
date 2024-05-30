package org.sarangchurch.growing.attendance.domain.user;

public class User {
    private final Long id;
    private final boolean isAdmin;

    public User(Long id, boolean isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
