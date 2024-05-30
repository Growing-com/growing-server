package org.sarangchurch.growing.term.domain.term;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum TermStatus implements EnumModel {
    PENDING("대기"),
    ACTIVE("진행"),
    CLOSED("종료")
    ;

    private final String value;

    TermStatus(String value) {
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
