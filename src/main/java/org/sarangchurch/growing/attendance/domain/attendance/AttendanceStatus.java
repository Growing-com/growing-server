package org.sarangchurch.growing.attendance.domain.attendance;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum AttendanceStatus implements EnumModel {
    ATTEND("출석"),
    ABSENT("결석"),
    ONLINE("온라인");

    private final String value;

    AttendanceStatus(String value) {
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
