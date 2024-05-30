package org.sarangchurch.growing.user.domain;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum Sex implements EnumModel {
    MALE("남자"),
    FEMALE("여자");

    private final String value;

    Sex(String value) {
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
