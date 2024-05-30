package org.sarangchurch.growing.user.domain;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum Role implements EnumModel {
    ADMIN("관리자"),
    MANAGER("매니저"),
    NORMAL("일반");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
