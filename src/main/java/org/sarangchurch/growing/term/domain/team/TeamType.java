package org.sarangchurch.growing.term.domain.team;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum TeamType implements EnumModel {
    LAMP("등불"),
    TREE("나무모임"),
    PLANT("순모임"),
    NEW("새가족반"),
    OUT("라인아웃");

    private final String value;

    TeamType(String value) {
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
