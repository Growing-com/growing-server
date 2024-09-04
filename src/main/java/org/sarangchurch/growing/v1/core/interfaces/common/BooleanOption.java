package org.sarangchurch.growing.v1.core.interfaces.common;

public enum BooleanOption {
    YES("네"),
    NO("아니요")
    ;

    private final String value;

    BooleanOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
