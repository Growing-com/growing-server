package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily;

public enum NewFamilyVisitReason {
    DISCIPLE_TRAINING("훈련을 받고 싶어서"),
    INTRODUCE("아는 사람 소개로"),
    RELIGION("종교를 가져야겠다는 생각에"),
    NEW_LIFE_FESTIVAL("대학부 새생명축제(전도집회)를 계기로"),
    ETC("기타")
    ;

    private final String value;

    NewFamilyVisitReason(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
