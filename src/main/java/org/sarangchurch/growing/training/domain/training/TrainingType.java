package org.sarangchurch.growing.training.domain.training;

import org.sarangchurch.growing.core.enumconfig.EnumModel;

public enum TrainingType implements EnumModel {
    DISCIPLE_SCHOOL_A("제자학교A"),
    DISCIPLE_SCHOOL_B("제자학교B"),

    INFANT_BAPTISM("유아 세례"),
    NORMAL_BAPTISM("성인 세례"),
    CONFIRMATION("입교"),
    MILITARY_BAPTISM("군대 세례"),
    PRE_BAPTISM("학습"),

    XEE("전도폭발")
    ;

    private final String value;

    TrainingType(String value) {
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
