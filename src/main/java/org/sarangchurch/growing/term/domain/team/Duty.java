package org.sarangchurch.growing.term.domain.team;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum Duty implements EnumModel {
    PASTOR("교역자"),
    GANSA("간사"),
    CODY("코디"),
    LEADER("리더"),
    MEMBER("조원"),
    NEW_COMER("새가족"),
    OUT("라인아웃");

    private final String value;

    Duty(String value) {
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
