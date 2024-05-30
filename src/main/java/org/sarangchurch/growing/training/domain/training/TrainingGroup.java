package org.sarangchurch.growing.training.domain.training;

import lombok.Getter;
import org.sarangchurch.growing.core.enumconfig.EnumModel;

@Getter
public enum TrainingGroup implements EnumModel {
    DISCIPLE_SCHOOL("제자학교", new TrainingType[]{
            TrainingType.DISCIPLE_SCHOOL_A,
            TrainingType.DISCIPLE_SCHOOL_B
    }),
    BAPTISM("세례", new TrainingType[]{
            TrainingType.INFANT_BAPTISM,
            TrainingType.NORMAL_BAPTISM,
            TrainingType.CONFIRMATION,
            TrainingType.MILITARY_BAPTISM,
            TrainingType.PRE_BAPTISM
    }),
    ETC("기타 훈련", new TrainingType[]{
            TrainingType.XEE
    });

    private final String value;
    private final TrainingType[] trainingTypes;

    TrainingGroup(String value, TrainingType[] trainingTypes) {
        this.value = value;
        this.trainingTypes = trainingTypes;
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
